import java.io.File;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Scanner;

// classe data ------------------------------------------------------------------------------------------------------

class Data {
    private int dia;
    private int mes;
    private int ano;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public static Data parseData(String s) {
        String[] partes = s.split("-");
        return new Data(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
    }

    public String formatar() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

// classe hora ------------------------------------------------------------------------------------------------------

class Hora {
    private int hora;
    private int minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public static Hora parseHora(String s) {
        String[] partes = s.split(":");
        return new Hora(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
    }

    public String formatar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}

// classe restaurante ------------------------------------------------------------------------------------------------

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

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getAvaliacao() {
        return avaliacao;
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
        String[] tiposCozinha = scanner.next().split(";");
        int faixaPreco = scanner.next().length();
        String[] horario = scanner.next().split("-");
        Hora horarioAbertura = Hora.parseHora(horario[0]);
        Hora horarioFechamento = Hora.parseHora(horario[1]);
        Data dataAbertura = Data.parseData(scanner.next());
        boolean aberto = scanner.next().equals("true");

        scanner.close();

        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }

    public String formatar() {
        String resp = "[";

        resp += id + " ## ";
        resp += nome + " ## ";
        resp += cidade + " ## ";
        resp += capacidade + " ## ";
        resp += avaliacao + " ## [";

        for (int i = 0; i < tiposCozinha.length; i++) {
            resp += tiposCozinha[i];

            if (i < tiposCozinha.length - 1) {
                resp += ",";
            }
        }

        resp += "] ## ";

        for (int i = 0; i < faixaPreco; i++) {
            resp += "$";
        }

        resp += " ## ";
        resp += horarioAbertura.formatar() + "-" + horarioFechamento.formatar();
        resp += " ## ";
        resp += dataAbertura.formatar();
        resp += " ## ";
        resp += aberto + "]";

        return resp;
    }
}

// colecao de restaurantes ------------------------------------------------------------------------------------------

class ColecaoRestaurantes {
    private Restaurante[] restaurantes;
    private int tamanho;

    public void lerCsv(String path) throws Exception {
        Scanner arquivo = new Scanner(new File(path));

        restaurantes = new Restaurante[10000];
        tamanho = 0;

        arquivo.nextLine();

        while (arquivo.hasNextLine()) {
            restaurantes[tamanho] = Restaurante.parseRestaurante(arquivo.nextLine());
            tamanho++;
        }

        arquivo.close();
    }

    public static ColecaoRestaurantes lerCsv() throws Exception {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes();
        colecao.lerCsv("/tmp/restaurantes.csv");
        return colecao;
    }

    public Restaurante buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (restaurantes[i].getId() == id) {
                return restaurantes[i];
            }
        }

        return null;
    }
}

// celula dupla -----------------------------------------------------------------------------------------------------

class CelulaDupla {
    public Restaurante elemento;
    public CelulaDupla ant;
    public CelulaDupla prox;

    public CelulaDupla() {
        this(null);
    }

    public CelulaDupla(Restaurante elemento) {
        this.elemento = elemento;
        this.ant = null;
        this.prox = null;
    }
}

// lista dupla flexivel ---------------------------------------------------------------------------------------------

class ListaDupla {
    private CelulaDupla primeiro;
    private CelulaDupla ultimo;
    private int tamanho;

    public ListaDupla() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
        tamanho = 0;
    }

    public void inserirFim(Restaurante x) {
        CelulaDupla nova = new CelulaDupla(x);
        ultimo.prox = nova;
        nova.ant = ultimo;
        ultimo = nova;
        tamanho++;
    }

    public CelulaDupla getPrimeiroReal() {
        return primeiro.prox;
    }

    public CelulaDupla getUltimo() {
        return ultimo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public Restaurante[] toArray() {
        Restaurante[] array = new Restaurante[tamanho];
        CelulaDupla i = primeiro.prox;
        int pos = 0;

        while (i != null) {
            array[pos++] = i.elemento;
            i = i.prox;
        }

        return array;
    }

    public void fromArray(Restaurante[] array) {
        CelulaDupla i = primeiro.prox;
        int pos = 0;

        while (i != null) {
            i.elemento = array[pos++];
            i = i.prox;
        }
    }

    public void mostrar() {
        CelulaDupla i = primeiro.prox;

        while (i != null) {
            System.out.println(i.elemento.formatar());
            i = i.prox;
        }
    }
}

// classe principal -------------------------------------------------------------------------------------------------

public class OrdenacaoQuicksortListaDuplaFlexivel {

    static long comparacoes = 0;
    static long movimentacoes = 0;

    // comparar ----------------------------------------------------------------------------------------------------

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

    // swap --------------------------------------------------------------------------------------------------------

    public static void swap(Restaurante[] array, int i, int j) {
        Restaurante tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        movimentacoes += 3;
    }

    // quicksort ---------------------------------------------------------------------------------------------------

    public static void quicksort(Restaurante[] array, int esq, int dir) {
        int i = esq;
        int j = dir;
        Restaurante pivo = array[(esq + dir) / 2];

        while (i <= j) {
            while (comparar(array[i], pivo) < 0) {
                i++;
            }

            while (comparar(array[j], pivo) > 0) {
                j--;
            }

            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if (esq < j) {
            quicksort(array, esq, j);
        }

        if (i < dir) {
            quicksort(array, i, dir);
        }
    }

    public static void ordenarLista(ListaDupla lista) {
        if (lista.getTamanho() <= 1) {
            return;
        }

        Restaurante[] array = lista.toArray();
        quicksort(array, 0, array.length - 1);
        lista.fromArray(array);
    }

    // main --------------------------------------------------------------------------------------------------------

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

        ordenarLista(lista);

        long fim = System.nanoTime();

        lista.mostrar();

        double tempo = (fim - inicio) / 1000000.0;

        FileWriter log = new FileWriter("885005_quicksort_flexivel.txt");
        log.write("885005\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo);
        log.close();

        scanner.close();
    }
}
