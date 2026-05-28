import java.io.File;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Scanner;

//comeco clase data ---------------------------------------------------------------------------------------------------

class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public static Data parseData(String s) {
        int a = 0;
        int m = 0;
        int d = 0;
        int i = 0;

        while (s.charAt(i) != '-') {
            a = a * 10 + (s.charAt(i) - '0');
            i++;
        }

        i++;

        while (s.charAt(i) != '-') {
            m = m * 10 + (s.charAt(i) - '0');
            i++;
        }

        i++;

        while (i < s.length()) {
            d = d * 10 + (s.charAt(i) - '0');
            i++;
        }

        return new Data(d, m, a);
    }

    public String formatar() {
        String nova = "";

        if (dia < 10) {
            nova += "0";
        }

        nova += dia + "/";

        if (mes < 10) {
            nova += "0";
        }

        nova += mes + "/";
        nova += ano;

        return nova;
    }
}

//comeco classe hora---------------------------------------------------------------------------------------------------

class Hora {
    private int hora;
    private int minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public static Hora parseHora(String s) {
        int h = 0;
        int m = 0;
        int i = 0;

        while (s.charAt(i) != ':') {
            h = h * 10 + (s.charAt(i) - '0');
            i++;
        }

        i++;

        while (i < s.length()) {
            m = m * 10 + (s.charAt(i) - '0');
            i++;
        }

        return new Hora(h, m);
    }

    public String formatar() {
        String nova = "";

        if (hora < 10) {
            nova += "0";
        }

        nova += hora + ":";

        if (minuto < 10) {
            nova += "0";
        }

        nova += minuto;

        return nova;
    }
}

//comeco da classe restaurante----------------------------------------------------------------------------------------

class Restaurante {
    private int id;
    private String nome;
    private String cidade;
    private int capacidade;
    private double avaliacao;
    private String[] tiposCozinha;
    private int faixaPreco;
    private Hora horarioAbertura;
    private Hora horarioFechamento;
    private Data dataAbertura;
    private boolean aberto;

    //construtor restaurante-----------------------------------------------------------------------------------------

    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tiposCozinha,
            int faixaPreco, Hora horarioAbertura, Hora horarioFechamento, Data dataAbertura, boolean aberto) {

        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha;
        this.faixaPreco = faixaPreco;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.dataAbertura = dataAbertura;
        this.aberto = aberto;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    //metodo que recebe a linha do csv, e organiza o restaurante------------------------------------------------------

    public static Restaurante parseRestaurante(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useLocale(Locale.US);
        scanner.useDelimiter(",");

        int id = scanner.nextInt();
        String nome = scanner.next();
        String cidade = scanner.next();
        int capacidade = scanner.nextInt();
        double avaliacao = scanner.nextDouble();

        // tipos de cozinha------------------------------------------------------------------------------------------

        String tipos = scanner.next();
        int quantidade = 1;

        for (int i = 0; i < tipos.length(); i++) {
            if (tipos.charAt(i) == ';') {
                quantidade++;
            }
        }

        String[] tiposCozinha = new String[quantidade];
        String aux = "";
        int pos = 0;

        for (int i = 0; i < tipos.length(); i++) {
            if (tipos.charAt(i) == ';') {
                tiposCozinha[pos] = aux;
                pos++;
                aux = "";
            } else {
                aux += tipos.charAt(i);
            }
        }

        tiposCozinha[pos] = aux;

        // faixa de preco--------------------------------------------------------------------------------------------

        int faixaPreco = scanner.next().length();

        // horarios--------------------------------------------------------------------------------------------------

        String horario = scanner.next();
        String h1 = "";
        String h2 = "";
        int i = 0;

        while (horario.charAt(i) != '-') {
            h1 += horario.charAt(i);
            i++;
        }

        i++;

        while (i < horario.length()) {
            h2 += horario.charAt(i);
            i++;
        }

        Hora horarioAbertura = Hora.parseHora(h1);
        Hora horarioFechamento = Hora.parseHora(h2);

        // data------------------------------------------------------------------------------------------------------

        Data dataAbertura = Data.parseData(scanner.next());

        // boolean---------------------------------------------------------------------------------------------------

        String abertoStr = scanner.next();
        boolean aberto = false;

        if (abertoStr.equals("true")) {
            aberto = true;
        }

        scanner.close();

        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }

    // formatacao----------------------------------------------------------------------------------------------------

    public String formatar() {
        String nova = "";

        nova += "[";
        nova += id + " ## ";
        nova += nome + " ## ";
        nova += cidade + " ## ";
        nova += capacidade + " ## ";
        nova += avaliacao + " ## ";
        nova += "[";

        for (int i = 0; i < tiposCozinha.length; i++) {
            nova += tiposCozinha[i];

            if (i < tiposCozinha.length - 1) {
                nova += ",";
            }
        }

        nova += "] ## ";

        for (int i = 0; i < faixaPreco; i++) {
            nova += "$";
        }

        nova += " ## ";
        nova += horarioAbertura.formatar();
        nova += "-";
        nova += horarioFechamento.formatar();
        nova += " ## ";
        nova += dataAbertura.formatar();
        nova += " ## ";
        nova += aberto;
        nova += "]";

        return nova;
    }
}

//comeco da colecao---------------------------------------------------------------------------------------------------

class ColecaoRestaurantes {
    private Restaurante[] restaurantes;
    private int tamanho;

    // leitura do csv------------------------------------------------------------------------------------------------

    public void lerCsv(String path) throws Exception {
        Scanner arquivo = new Scanner(new File(path));

        restaurantes = new Restaurante[10000];
        tamanho = 0;

        arquivo.nextLine();

        while (arquivo.hasNextLine()) {
            String linha = arquivo.nextLine();
            restaurantes[tamanho] = Restaurante.parseRestaurante(linha);
            tamanho++;
        }

        arquivo.close();
    }

    public static ColecaoRestaurantes lerCsv() throws Exception {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes();
        File arquivo = new File("/tmp/restaurantes.csv");

        if (arquivo.exists()) {
            colecao.lerCsv("/tmp/restaurantes.csv");
        } else {
            colecao.lerCsv("../tp02/restaurantes.csv");
        }

        return colecao;
    }

    // busca por id--------------------------------------------------------------------------------------------------

    public Restaurante buscarPorId(int id) {
        Restaurante resp = null;

        for (int i = 0; i < tamanho; i++) {
            if (restaurantes[i].getId() == id) {
                resp = restaurantes[i];
                i = tamanho;
            }
        }

        return resp;
    }
}

// no da trie---------------------------------------------------------------------------------------------------------

class NoTrie {
    public static final int TAM = 255;
    public NoTrie[] filhos;
    public Restaurante elemento;

    public NoTrie() {
        filhos = new NoTrie[TAM];
        elemento = null;
    }

    // hash do caractere---------------------------------------------------------------------------------------------

    public int hash(char c) {
        return ((int) c) % TAM;
    }

    // pegar filho---------------------------------------------------------------------------------------------------

    public NoTrie getFilho(char c) {
        return filhos[hash(c)];
    }

    // colocar filho-------------------------------------------------------------------------------------------------

    public void setFilho(char c, NoTrie no) {
        filhos[hash(c)] = no;
    }
}

// arvore trie com hash----------------------------------------------------------------------------------------------

class TrieHash {
    private NoTrie raiz;
    public long comparacoes;
    public long movimentacoes;

    public TrieHash() {
        raiz = new NoTrie();
        comparacoes = 0;
        movimentacoes = 0;
    }

    // inserir-------------------------------------------------------------------------------------------------------

    public void inserir(Restaurante r) {
        NoTrie atual = raiz;
        String nome = r.getNome();

        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);

            if (atual.getFilho(c) == null) {
                atual.setFilho(c, new NoTrie());
                movimentacoes++;
            }

            atual = atual.getFilho(c);
        }

        atual.elemento = r;
    }

    // pesquisar-----------------------------------------------------------------------------------------------------

    public Restaurante pesquisar(String nome) {
        NoTrie atual = raiz;
        Restaurante resp = null;
        boolean continuar = true;
        int i = 0;

        while (i < nome.length() && continuar) {
            char c = nome.charAt(i);
            NoTrie prox = atual.getFilho(c);
            comparacoes++;

            if (prox == null) {
                continuar = false;
            } else {
                System.out.print(c + " ");
                atual = prox;
                i++;
            }
        }

        if (continuar && i == nome.length() && atual.elemento != null) {
            resp = atual.elemento;
        }

        return resp;
    }
}

// classe principal---------------------------------------------------------------------------------------------------

public class ArvoreTrieHash {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        TrieHash trie = new TrieHash();

        // leitura ids-----------------------------------------------------------------------------------------------

        String entrada = scanner.nextLine();

        while (!entrada.equals("-1")) {
            int id = Integer.parseInt(entrada.trim());
            Restaurante r = colecao.buscarPorId(id);

            if (r != null) {
                trie.inserir(r);
            }

            entrada = scanner.nextLine();
        }

        long inicio = System.nanoTime();

        // pesquisas------------------------------------------------------------------------------------------------

        entrada = scanner.nextLine();

        while (!entrada.equals("FIM")) {
            Restaurante resp = trie.pesquisar(entrada);

            if (resp == null) {
                System.out.println("NAO");
            } else {
                System.out.println("SIM " + resp.formatar());
            }

            entrada = scanner.nextLine();
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1000000.0;

        // arquivo log-----------------------------------------------------------------------------------------------

        FileWriter log = new FileWriter("885005_trie_hash.txt");
        log.write("885005\t" + trie.comparacoes + "\t" + trie.movimentacoes + "\t" + tempo);
        log.close();

        scanner.close();
    }
}
