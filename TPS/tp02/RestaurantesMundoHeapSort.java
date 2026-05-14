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
        if (dia < 10)
            nova += "0";
        nova += dia + "/";
        if (mes < 10)
            nova += "0";
        nova += mes + "/";
        nova += ano;
        return nova;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
        }

        public int getAno() {
            return ano;
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

    public String getNome() { return nome; }
    public String getCidade() { return cidade; }

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
            if (i < tiposCozinha.length - 1)
                s += ",";
        }

        s += "] ## ";

        for (int i = 0; i < faixaPreco; i++)
            s += "$";

        s += " ## " + horarioAbertura.formatar() + "-" + horarioFechamento.formatar();
        s += " ## " + dataAbertura.formatar();
        s += " ## " + aberto + "]";

        return s;
    }

    public Data getDataAbertura() {
        return dataAbertura;
     }

    int getId() {
        return id;
    }

    int getId() {
        throw new UnsupportedOperationException("Not supported yet.");
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

// heapSort---------------------------------------------------------------------------------------------------


public class RestaurantesMundoHeapSort {
    static int comparacoes = 0;
    static int movimentacoes = 0;
    // compara por data de abertura; em caso de empate, compara por nome
    public static boolean maior(Restaurante a, Restaurante b) {
        comparacoes++;
        if (a.getDataAbertura().getAno() > b.getDataAbertura().getAno()) return true;
        if (a.getDataAbertura().getAno() < b.getDataAbertura().getAno()) return false;

        comparacoes++;
        if (a.getDataAbertura().getMes() > b.getDataAbertura().getMes()) return true;
        if (a.getDataAbertura().getMes() < b.getDataAbertura().getMes()) return false;

        comparacoes++;
        if (a.getDataAbertura().getDia() > b.getDataAbertura().getDia()) return true;
        if (a.getDataAbertura().getDia() < b.getDataAbertura().getDia()) return false;

        comparacoes++;
        return a.getNome().compareTo(b.getNome()) > 0;
    }

    public static void swap(Restaurante[] array, int i, int j) {
        Restaurante temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        movimentacoes += 3;
    }

    public static void reconstruir(Restaurante[] array, int tamHeap) {
        int i = 1;
        while (i <= tamHeap / 2) {
            int filho = getMaiorFilho(array, i, tamHeap);

            if (maior(array[filho], array[i])) {
                swap(array, i, filho);
                i = filho;
            } else {
                i = tamHeap;
            }
        }
    }

    public static int getMaiorFilho(Restaurante[] array, int i, int tamHeap) {
        int filho;
        if (2 * i == tamHeap || !maior(array[2 * i + 1], array[2 * i])) {
            filho = 2 * i;
        } else {
            filho = 2 * i + 1;
        }
        return filho;
    }

    public static void construir(Restaurante[] array, int tamHeap) {
        for (int i = tamHeap; i > 1 && maior(array[i], array[i / 2]); i /= 2) {
            swap(array, i, i / 2);
        }
    }
    public static void heapsort(Restaurante[] array, int n) {
        Restaurante[] tmp = new Restaurante[n + 1];
        for (int i = 0; i < n; i++) {
            tmp[i + 1] = array[i];
            movimentacoes++;
        }
        for (int tamHeap = 2; tamHeap <= n; tamHeap++) {
            construir(tmp, tamHeap);
        }
        int tamHeap = n;
        while (tamHeap > 1) {
            swap(tmp, 1, tamHeap--);
            reconstruir(tmp, tamHeap);
        }
        for (int i = 0; i < n; i++) {
            array[i] = tmp[i + 1];
            movimentacoes++;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanf = new Scanner(System.in);
        ColecaoRestaurantes rest = ColecaoRestaurantes.lerCsv();
        Restaurante[] sel = new Restaurante[1000];
        int n = 0;
        int id = scanf.nextInt();
        while (id != -1) {
            Restaurante r = rest.buscarPorId(id);
            if (r != null) {
                sel[n++] = r;
            }
            id = scanf.nextInt();
        }
        long ini = System.nanoTime();
        heapsort(sel, n);
        long fim = System.nanoTime();
        for (int i = 0; i < n; i++) {
            System.out.println(sel[i].formatar());
        }
        FileWriter log = new FileWriter("885005_heapsort.txt");
        log.write("885005\t" + comparacoes + "\t" + movimentacoes + "\t" + ((fim - ini) / 1000000.0));
        log.close();
        scanf.close();
    }
}