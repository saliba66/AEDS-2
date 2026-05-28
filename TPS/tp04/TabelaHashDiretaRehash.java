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

// tabela hash direta com rehash--------------------------------------------------------------------------------------

class TabelaHashRestaurantes {
    private Restaurante[] tabela;
    private int tamanho;
    public long comparacoes;
    public long movimentacoes;

    public TabelaHashRestaurantes() {
        tamanho = 83;
        tabela = new Restaurante[tamanho];
        comparacoes = 0;
        movimentacoes = 0;
    }

    // soma dos caracteres do nome-----------------------------------------------------------------------------------

    private int somaCaracteres(String nome) {
        int soma = 0;

        for (int i = 0; i < nome.length(); i++) {
            soma += nome.charAt(i);
        }

        return soma;
    }

    // funcao hash---------------------------------------------------------------------------------------------------

    private int hash(String nome) {
        return somaCaracteres(nome) % tamanho;
    }

    // funcao rehash-------------------------------------------------------------------------------------------------

    private int rehash(String nome) {
        return (hash(nome) + 1) % tamanho;
    }

    // inserir-------------------------------------------------------------------------------------------------------

    public boolean inserir(Restaurante x) {
        boolean resp = false;

        if (x != null) {
            int pos = hash(x.getNome());

            if (tabela[pos] == null) {
                tabela[pos] = x;
                movimentacoes++;
                resp = true;
            } else {
                pos = rehash(x.getNome());

                if (tabela[pos] == null) {
                    tabela[pos] = x;
                    movimentacoes++;
                    resp = true;
                }
            }
        }

        return resp;
    }

    // pesquisar-----------------------------------------------------------------------------------------------------

    public int pesquisar(String nome) {
        int resp = -1;
        int pos = hash(nome);

        comparacoes++;

        if (tabela[pos] != null && tabela[pos].getNome().equals(nome)) {
            resp = pos;
        } else {
            pos = rehash(nome);
            comparacoes++;

            if (tabela[pos] != null && tabela[pos].getNome().equals(nome)) {
                resp = pos;
            }
        }

        return resp;
    }

    // mostrar pesquisa----------------------------------------------------------------------------------------------

    public void mostrarPesquisa(String nome) {
        int pos = pesquisar(nome);

        if (pos == -1) {
            System.out.println("-1");
        } else {
            System.out.println(pos + " " + tabela[pos].formatar());
        }
    }
}

// classe principal---------------------------------------------------------------------------------------------------

public class TabelaHashDiretaRehash {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        TabelaHashRestaurantes tabela = new TabelaHashRestaurantes();

        // leitura ids-----------------------------------------------------------------------------------------------

        String entrada = scanner.nextLine();

        while (!entrada.equals("-1")) {
            int id = Integer.parseInt(entrada.trim());
            Restaurante r = colecao.buscarPorId(id);

            if (r != null) {
                boolean inseriu = tabela.inserir(r);

                if (!inseriu) {
                    System.out.println(r.getNome());
                }
            }

            entrada = scanner.nextLine();
        }

        long inicio = System.nanoTime();

        // pesquisas------------------------------------------------------------------------------------------------

        entrada = scanner.nextLine();

        while (!entrada.equals("FIM")) {
            tabela.mostrarPesquisa(entrada);
            entrada = scanner.nextLine();
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1000000.0;

        // arquivo log-----------------------------------------------------------------------------------------------

        FileWriter log = new FileWriter("885005_hash_rehash.txt");
        log.write("885005\t" + tabela.comparacoes + "\t" + tabela.movimentacoes + "\t" + tempo);
        log.close();

        scanner.close();
    }
}
