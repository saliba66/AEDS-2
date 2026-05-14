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
        int a = 0, m = 0, d = 0, i = 0;

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

//comeco classe hora---------------------------------------------------------------------------------------------------


class Hora {
    private int hora;
    private int minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public static Hora parseHora(String s) {
        int h = 0, m = 0, i = 0;

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

//comeco da classe restaurante---------------------------------------------------------------------------------------------------

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

    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
            String[] tiposCozinha, int faixaPreco, Hora horarioAbertura,
            Hora horarioFechamento, Data dataAbertura, boolean aberto) {

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

    public String getNome() {
        return nome;
     }

     public String getCidade() {
         return cidade;
     }

     public int getId() {
         return id;
}
    public static Restaurante parseRestaurante(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useLocale(Locale.US);
        scanner.useDelimiter(",");

        int id = scanner.nextInt();
        String nome = scanner.next();
        String cidade = scanner.next();
        int capacidade = scanner.nextInt();
        double avaliacao = scanner.nextDouble();

        String tipos = scanner.next();
        int qtd = 1;
        for (int i = 0; i < tipos.length(); i++)
            if (tipos.charAt(i) == ';') qtd++;

        String[] tiposCozinha = new String[qtd];
        String aux = "";
        int pos = 0;

        for (int i = 0; i < tipos.length(); i++) {
            if (tipos.charAt(i) == ';') {
                tiposCozinha[pos++] = aux;
                aux = "";
            } else {
                aux += tipos.charAt(i);
            }
        }
        tiposCozinha[pos] = aux;

        int faixaPreco = scanner.next().length();

        String horario = scanner.next();
        String h1 = "", h2 = "";
        int i = 0;

        while (horario.charAt(i) != '-') h1 += horario.charAt(i++);
        i++;
        while (i < horario.length()) h2 += horario.charAt(i++);

        Hora ha = Hora.parseHora(h1);
        Hora hf = Hora.parseHora(h2);

        Data data = Data.parseData(scanner.next());

        String ab = scanner.next();
        boolean aberto = (ab.length() == 4 &&
                          ab.charAt(0) == 't');

        scanner.close();

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, ha, hf, data, aberto);
    }

    public String formatar() {
        String s = "[" + id + " ## " + nome + " ## " + cidade + " ## ";
        s += capacidade + " ## " + avaliacao + " ## [";

        for (int i = 0; i < tiposCozinha.length; i++) {
            s += tiposCozinha[i];
            if (i < tiposCozinha.length - 1) s += ",";
        }

        s += "] ## ";

        for (int i = 0; i < faixaPreco; i++) s += "$";

        s += " ## " + horarioAbertura.formatar() + "-" + horarioFechamento.formatar();
        s += " ## " + dataAbertura.formatar();
        s += " ## " + aberto + "]";

        return s;
    }
}

//colecao---------------------------------------------------------------------------------------------------


class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    public void lerCsv(String path) throws Exception {
        Scanner arq = new Scanner(new File(path));
        restaurantes = new Restaurante[10000];
        tamanho = 0;

        arq.nextLine();

        while (arq.hasNextLine()) {
            restaurantes[tamanho++] = Restaurante.parseRestaurante(arq.nextLine());
        }
        arq.close();
    }

    public static ColecaoRestaurantes lerCsv() throws Exception {
        ColecaoRestaurantes c = new ColecaoRestaurantes();
        c.lerCsv("/tmp/restaurantes.csv");
        return c;
    }

    public Restaurante buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++)
            if (restaurantes[i].getId() == id)
                return restaurantes[i];
        return null;
    }
}

// mergesort---------------------------------------------------------------------------------------------------


public class RestaurantesMundoMergesort {

    static int comparacoes = 0;
    static int movimentacoes = 0;

    public static boolean menor(Restaurante a, Restaurante b) {
        comparacoes++;
        int c = a.getCidade().compareTo(b.getCidade());

        if (c < 0) return true;
        if (c > 0) return false;

        comparacoes++;
        return a.getNome().compareTo(b.getNome()) <= 0;
    }

    public static void mergesort(Restaurante[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergesort(arr, l, m);
            mergesort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    public static void merge(Restaurante[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Restaurante[] L = new Restaurante[n1];
        Restaurante[] R = new Restaurante[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[l + i];
        for (int j = 0; j < n2; j++) R[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            if (menor(L[i], R[j])) arr[k++] = L[i++];
            else arr[k++] = R[j++];
            movimentacoes++;
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ColecaoRestaurantes col = ColecaoRestaurantes.lerCsv();

        Restaurante[] sel = new Restaurante[1000];
        int n = 0;

        int id = sc.nextInt();
        while (id != -1) {
            Restaurante r = col.buscarPorId(id);
            if (r != null) sel[n++] = r;
            id = sc.nextInt();
        }

        long ini = System.nanoTime();
        mergesort(sel, 0, n - 1);
        long fim = System.nanoTime();

        for (int i = 0; i < n; i++)
            System.out.println(sel[i].formatar());

        FileWriter log = new FileWriter("885005_mergesort.txt");
        log.write("885005\t" + comparacoes + "\t" + movimentacoes + "\t" + ((fim - ini)/1000000.0));
        log.close();

        sc.close();
    }
}