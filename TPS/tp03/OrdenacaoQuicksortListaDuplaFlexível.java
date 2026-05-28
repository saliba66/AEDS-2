import java.io.File;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Scanner;

//comeco clase data 

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

        if (dia < 10) nova += "0";
        nova += dia + "/";

        if (mes < 10) nova += "0";
        nova += mes + "/";

        nova += ano;

        return nova;
    }
}

//comeco classe hora----------------------------------------------------------------------------------------------------------------------------------------------------------

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

        if (hora < 10) nova += "0";
        nova += hora + ":";

        if (minuto < 10) nova += "0";
        nova += minuto;

        return nova;
    }
}

//comeco da classe restaurante----------------------------------------------------------------------------------------------------------

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

//construtor restaurante---------------------------------------------------------------------------------------------------------------

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

    public double getAvaliacao() {
        return avaliacao;
    }

//metodo que recebe a linha do csv, e organiza o restaurante------------------------------------------------------------------------------------

    public static Restaurante parseRestaurante(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useLocale(Locale.US);
        scanner.useDelimiter(",");

        int id = scanner.nextInt();
        String nome = scanner.next();
        String cidade = scanner.next();
        int capacidade = scanner.nextInt();
        double avaliacao = scanner.nextDouble();

// Tipos de cozinha----------------------------------------------------------------------------------------------------

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

// faixa de preco------------------------------------------------------------------------------------------------------

        int faixaPreco = scanner.next().length();

// Horários------------------------------------------------------------------------------------------------------------

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

// Data-------------------------------------------------------------------------------------------------------------

        Data dataAbertura = Data.parseData(scanner.next());

// boolean-------------------------------------------------------------------------------------------------------------

        String abertoStr = scanner.next();
        boolean aberto = abertoStr.equals("true");

        scanner.close();

        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }

// formatacao-------------------------------------------------------------------------------------------------------------

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

//comeco da colecao----------------------------------------------------------------------------------------------------------

class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

// leitura csv-------------------------------------------------------------------------------------------------------------

    public void lerCsv(String path) throws Exception {
        Scanner arquivo = new Scanner(new File(path));
        restaurantes = new Restaurante[10000];
        tamanho = 0;

        arquivo.nextLine();

        while (arquivo.hasNextLine()) {
            String linha = arquivo.nextLine();
            Restaurante novo = Restaurante.parseRestaurante(linha);
            restaurantes[tamanho] = novo;
            tamanho++;
        }

        arquivo.close();
    }

    public static ColecaoRestaurantes lerCsv() throws Exception {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes();
        colecao.lerCsv("/tmp/restaurantes.csv");
        return colecao;
    }

// busca-------------------------------------------------------------------------------------------------------------

    public Restaurante buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (restaurantes[i].getId() == id) {
                return restaurantes[i];
            }
        }

        return null;
    }
}

// celula dupla----------------------------------------------------------------------------------------------------------

class CelulaDupla {
    Restaurante elemento;
    CelulaDupla prox;
    CelulaDupla ant;

    public CelulaDupla() {
        this.elemento = null;
        this.prox = null;
        this.ant = null;
    }

    public CelulaDupla(Restaurante elemento) {
        this.elemento = elemento;
        this.prox = null;
        this.ant = null;
    }
}

// lista dupla flexivel----------------------------------------------------------------------------------------------------------

class ListaDupla {
    private CelulaDupla primeiro;
    private CelulaDupla ultimo;

    public ListaDupla() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
    }

// inserir fim-------------------------------------------------------------------------------------------------------------

    public void inserirFim(Restaurante x) {
        CelulaDupla nova = new CelulaDupla(x);

        ultimo.prox = nova;
        nova.ant = ultimo;
        ultimo = nova;
    }

    public CelulaDupla getPrimeiroReal() {
        return primeiro.prox;
    }

    public CelulaDupla getUltimo() {
        return ultimo;
    }

// mostrar-------------------------------------------------------------------------------------------------------------

    public void mostrar() {
        CelulaDupla i = primeiro.prox;

        while (i != null) {
            System.out.println(i.elemento.formatar());
            i = i.prox;
        }
    }
}

//classe principal----------------------------------------------------------------------------------------------------------

public class OrdenacaoQuicksortListaDuplaFlexivel{

    static long comparacoes = 0;
    static long movimentacoes = 0;

// comparar-------------------------------------------------------------------------------------------------------------
// Ordena por avaliacao crescente
// Se a avaliacao for igual, ordena por nome crescente

    public static int comparar(Restaurante a, Restaurante b) {
        comparacoes++;

        if (a.getAvaliacao() < b.getAvaliacao()) {
            return -1;
        } else if (a.getAvaliacao() > b.getAvaliacao()) {
            return 1;
        } else {
            comparacoes++;
            return a.getNome().compareTo(b.getNome());
        }
    }

// swap-------------------------------------------------------------------------------------------------------------
// troca apenas os elementos das células

    public static void swap(CelulaDupla a, CelulaDupla b) {
        Restaurante tmp = a.elemento;
        a.elemento = b.elemento;
        b.elemento = tmp;

        movimentacoes += 3;
    }

// quicksort-------------------------------------------------------------------------------------------------------------

    public static void quicksort(CelulaDupla esq, CelulaDupla dir) {
        CelulaDupla i = esq;
        CelulaDupla j = dir;
        Restaurante pivo = esq.elemento;

        while (i != null && j != null && i.ant != j && i != j.prox) {

            while (comparar(i.elemento, pivo) < 0) {
                i = i.prox;
            }

            while (comparar(j.elemento, pivo) > 0) {
                j = j.ant;
            }

            if (i != null && j != null && i.ant != j && i != j.prox) {
                swap(i, j);
                i = i.prox;
                j = j.ant;
            }
        }

        if (esq != null && j != null && esq != j && esq.ant != j) {
            quicksort(esq, j);
        }

        if (i != null && dir != null && i != dir && i.ant != dir) {
            quicksort(i, dir);
        }
    }

// main-------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();

        ListaDupla lista = new ListaDupla();

        int id = scanner.nextInt();

        while (id != -1) {
            Restaurante r = colecao.buscarPorId(id);

            if (r != null) {
                lista.inserirFim(r);
            }

            id = scanner.nextInt();
        }

        long inicio = System.nanoTime();

        if (lista.getPrimeiroReal() != null) {
            quicksort(lista.getPrimeiroReal(), lista.getUltimo());
        }

        long fim = System.nanoTime();

        lista.mostrar();

        double tempo = (fim - inicio) / 1000000.0;

        FileWriter log = new FileWriter("885005_quicksort_flexivel.txt");
        log.write("885005\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo);
        log.close();

        scanner.close();
    }
}