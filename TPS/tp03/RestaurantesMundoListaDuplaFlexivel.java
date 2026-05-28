import java.io.File;
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

//comeco classe hora----------------------------------------------------------------------------------------------------------------------------------------------------------

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
        boolean aberto = false;

        if (abertoStr.equals("true")) {
            aberto = true;
        }

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

        arquivo.nextLine(); // pula cabeçalho

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
    private int tamanho;

    public ListaDupla() {
        primeiro = new CelulaDupla(); // célula cabeça
        ultimo = primeiro;
        tamanho = 0;
    }

// inserir inicio-------------------------------------------------------------------------------------------------------------

    public void inserirInicio(Restaurante x) {
        CelulaDupla nova = new CelulaDupla(x);

        nova.ant = primeiro;
        nova.prox = primeiro.prox;

        if (primeiro == ultimo) {
            ultimo = nova;
        } else {
            primeiro.prox.ant = nova;
        }

        primeiro.prox = nova;
        tamanho++;
    }

// inserir fim-------------------------------------------------------------------------------------------------------------

    public void inserirFim(Restaurante x) {
        CelulaDupla nova = new CelulaDupla(x);

        ultimo.prox = nova;
        nova.ant = ultimo;
        ultimo = nova;

        tamanho++;
    }

// inserir em posicao-------------------------------------------------------------------------------------------------------------

    public void inserir(Restaurante x, int pos) throws Exception {
        if (pos < 0 || pos > tamanho) {
            throw new Exception("Erro ao inserir!");
        }

        if (pos == 0) {
            inserirInicio(x);
        } else if (pos == tamanho) {
            inserirFim(x);
        } else {
            CelulaDupla i = primeiro;

            for (int j = 0; j < pos; j++) {
                i = i.prox;
            }

            CelulaDupla nova = new CelulaDupla(x);

            nova.ant = i;
            nova.prox = i.prox;
            i.prox.ant = nova;
            i.prox = nova;

            tamanho++;
        }
    }

// remover inicio-------------------------------------------------------------------------------------------------------------

    public Restaurante removerInicio() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover!");
        }

        CelulaDupla tmp = primeiro.prox;
        Restaurante resp = tmp.elemento;

        primeiro.prox = tmp.prox;

        if (tmp == ultimo) {
            ultimo = primeiro;
        } else {
            tmp.prox.ant = primeiro;
        }

        tmp.prox = null;
        tmp.ant = null;

        tamanho--;

        return resp;
    }

// remover fim-------------------------------------------------------------------------------------------------------------

    public Restaurante removerFim() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover!");
        }

        Restaurante resp = ultimo.elemento;
        CelulaDupla tmp = ultimo;

        ultimo = ultimo.ant;
        ultimo.prox = null;

        tmp.ant = null;

        tamanho--;

        return resp;
    }

// remover em posicao-------------------------------------------------------------------------------------------------------------

    public Restaurante remover(int pos) throws Exception {
        if (pos < 0 || pos >= tamanho) {
            throw new Exception("Erro ao remover!");
        }

        if (pos == 0) {
            return removerInicio();
        } else if (pos == tamanho - 1) {
            return removerFim();
        } else {
            CelulaDupla i = primeiro.prox;

            for (int j = 0; j < pos; j++) {
                i = i.prox;
            }

            Restaurante resp = i.elemento;

            i.ant.prox = i.prox;
            i.prox.ant = i.ant;

            i.prox = null;
            i.ant = null;

            tamanho--;

            return resp;
        }
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

public class RestaurantesMundoListaDuplaFlexivel {

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

        int quantidadeComandos = scanner.nextInt();

        for (int i = 0; i < quantidadeComandos; i++) {
            String comando = scanner.next();

            if (comando.equals("II")) {
                int idInserir = scanner.nextInt();
                Restaurante r = colecao.buscarPorId(idInserir);
                lista.inserirInicio(r);

            } else if (comando.equals("IF")) {
                int idInserir = scanner.nextInt();
                Restaurante r = colecao.buscarPorId(idInserir);
                lista.inserirFim(r);

            } else if (comando.equals("I*")) {
                int pos = scanner.nextInt();
                int idInserir = scanner.nextInt();
                Restaurante r = colecao.buscarPorId(idInserir);
                lista.inserir(r, pos);

            } else if (comando.equals("RI")) {
                Restaurante removido = lista.removerInicio();
                System.out.println("(R)" + removido.getNome());

            } else if (comando.equals("RF")) {
                Restaurante removido = lista.removerFim();
                System.out.println("(R)" + removido.getNome());

            } else if (comando.equals("R*")) {
                int pos = scanner.nextInt();
                Restaurante removido = lista.remover(pos);
                System.out.println("(R)" + removido.getNome());
            }
        }

        lista.mostrar();

        scanner.close();
    }
}