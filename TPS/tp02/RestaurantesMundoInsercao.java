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

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public static Data parseData(String s) {
        int a = 0;
        int m = 0;
        int d = 0;
        int i = 0;
        while (s.charAt(i) != '-') {
            a = a * 10 + (s.charAt(i) - '0');    //formatação da data, recebe string como parametro entao fazemos essa conta para transformar em int 
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

    public String formatar() {                              //formatação como pede a saida
        String nova = "";
        if (dia < 10) {
            nova += "0";
        }
        nova += dia;
        nova += '/';
        if (mes < 10) {
            nova += "0";
        }
        nova += mes;
        nova += '/';
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

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public static Hora parseHora(String s) {
        int h = 0;
        int m = 0;
        int i = 0;
        while (s.charAt(i) != ':') {                            //mesma coisa da data, recebe string e passa para int
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
        nova += hora;
        nova += ':';
        if (minuto < 10) {
            nova += "0";
        }
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

    public String getCidade() {
        return cidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public String[] getTiposCozinha() {
        return tiposCozinha;
    }

    public int getFaixaPreco() {
        return faixaPreco;
    }

    public Hora getHorarioAbertura() {
        return horarioAbertura;
    }

    public Hora getHorarioFechamento() {
        return horarioFechamento;
    }

    public Data getDataAbertura() {
        return dataAbertura;
    }

    public boolean getAberto() {
        return aberto;
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

        if (abertoStr.length() == 4 &&
            abertoStr.charAt(0) == 't' &&
            abertoStr.charAt(1) == 'r' &&
            abertoStr.charAt(2) == 'u' &&
            abertoStr.charAt(3) == 'e') {
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

        nova += '[';
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

    public int getTamanho() {
        return tamanho;
    }

    public Restaurante[] getRestaurantes() {
        return restaurantes;
    }

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

//classe principal----------------------------------------------------------------------------------------------------------

public class RestaurantesMundoInsercao {

    static int comparacoes = 0;
    static int movimentacoes = 0;

// insertion-------------------------------------------------------------------------------------------------------------

    public static void insercao(Restaurante[] array, int n) {
        for (int i = 1; i < n; i++) {
            Restaurante tmp = array[i];
            movimentacoes++;
            int j = i - 1;

            while (j >= 0) {
                comparacoes++;
                if (array[j].getCidade().compareTo(tmp.getCidade()) > 0) {  //faz o  metodo de inserção com um contador somando as movimentacoes e comparacoes dentro do while 
                    array[j + 1] = array[j];
                    movimentacoes++;
                    j--;
                } else {
                    break;
                }
            }

            array[j + 1] = tmp;
            movimentacoes++;
        }
    }

// main-------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();

        Restaurante[] selecionados = new Restaurante[1000];
        int n = 0;

        int id = scanner.nextInt();
        while (id != -1) {
            Restaurante r = colecao.buscarPorId(id);
            if (r != null) {
                selecionados[n] = r;
                n++;
            }
            id = scanner.nextInt();
        }

        long inicio = System.nanoTime();
        insercao(selecionados, n);
        long fim = System.nanoTime();

        for (int i = 0; i < n; i++) {
            System.out.println(selecionados[i].formatar());
        }

        double tempo = (fim - inicio) / 1000000.0;

        FileWriter log = new FileWriter("matricula_insercao.txt");
        log.write("885005\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo);
        log.close();

        scanner.close();
    }
}