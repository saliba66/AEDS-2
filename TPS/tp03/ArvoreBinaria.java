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

// no da arvore -----------------------------------------------------------------------------------------------------

class No {
    Restaurante elemento;
    No esq;
    No dir;

    public No(Restaurante elemento) {
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }
}

// arvore binaria ---------------------------------------------------------------------------------------------------

class Arvore {
    private No raiz;
    public long comparacoes;

    public Arvore() {
        raiz = null;
        comparacoes = 0;
    }

    public void inserir(Restaurante x) {
        raiz = inserir(x, raiz);
    }

    private No inserir(Restaurante x, No i) {
        if (i == null) {
            i = new No(x);
        } else {
            int cmp = x.getNome().compareTo(i.elemento.getNome());

            if (cmp < 0) {
                i.esq = inserir(x, i.esq);
            } else if (cmp > 0) {
                i.dir = inserir(x, i.dir);
            }
        }

        return i;
    }

    public boolean pesquisar(String nome) {
        System.out.print("raiz ");
        return pesquisar(nome, raiz);
    }

    private boolean pesquisar(String nome, No i) {
        if (i == null) {
            return false;
        }

        comparacoes++;

        int cmp = nome.compareTo(i.elemento.getNome());

        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            System.out.print("esq ");
            return pesquisar(nome, i.esq);
        } else {
            System.out.print("dir ");
            return pesquisar(nome, i.dir);
        }
    }

    public void mostrarCentral() {
        mostrarCentral(raiz);
    }

    private void mostrarCentral(No i) {
        if (i != null) {
            mostrarCentral(i.esq);
            System.out.println(i.elemento.formatar());
            mostrarCentral(i.dir);
        }
    }
}

// classe principal -------------------------------------------------------------------------------------------------

public class ArvoreBinaria {

    public static boolean isNumero(String s) {
        s = s.trim();

        if (s.length() == 0) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Arvore arvore = new Arvore();

        // leitura ids ----------------------------------------------------------------------------------------------

        String entrada = scanner.nextLine();

        while (!entrada.equals("-1")) {
            int id = Integer.parseInt(entrada.trim());
            Restaurante r = colecao.buscarPorId(id);

            if (r != null) {
                arvore.inserir(r);
            }

            entrada = scanner.nextLine();
        }

        arvore.comparacoes = 0;
        long inicio = System.nanoTime();

        // pesquisas ------------------------------------------------------------------------------------------------

        entrada = scanner.nextLine();

        while (!entrada.equals("FIM")) {
            boolean resp = arvore.pesquisar(entrada);

            if (resp) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }

            entrada = scanner.nextLine();
        }

        long fim = System.nanoTime();
        long tempo = fim - inicio;

        // mostrar em ordem -----------------------------------------------------------------------------------------

        arvore.mostrarCentral();

        // arquivo log ----------------------------------------------------------------------------------------------

        FileWriter log = new FileWriter("885005_arvore_binaria.txt");
        log.write("893070\t" + arvore.comparacoes + "\t" + tempo);
        log.close();

        scanner.close();
    }
}
