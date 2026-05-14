import java.util.Scanner;

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
        nova += 0;
        nova += dia;
    }
    else {
        nova += dia;
    }
    nova += '/';
    if (mes < 10) {
        nova += 0;
        nova += mes;
    } else {
        nova += mes;
    }
    nova += '/';
    nova += ano;
    return nova;
}
}

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
            nova += hora;
        } else {
            nova += hora;
        }
        nova += ':';
        if (minuto < 10) {
            nova += "0";
            nova += minuto;
        } else {
            nova += minuto;
        }
        return nova;
    }
}

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

    public static Restaurante parseRestaurante(String s) {
        String [] categorias = new String[10];
        int atual = 0;
        String texto = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',') {
                categorias[atual] = texto;
                atual++;
                texto = "";
            } else {
                texto += s.charAt(i);
            }
        }
        categorias[atual] = texto;
        int id = 0;
        for (int i = 0; i < categorias[0].length(); i++) {
            id = id * 10 + (categorias[0].charAt(i) - '0');
        }
        String nome = categorias[1];
        String cidade = categorias[2];
        int capacidade = 0;
        for (int i = 0; i < categorias[3].length(); i++) {
            capacidade = capacidade * 10 + (categorias[3].charAt(i) - '0');
        }
double avaliacao = 0;
double decimal = 0.1;
boolean depoisPonto = false;
for (int i = 0; i < categorias[4].length(); i++) {
    char c = categorias[4].charAt(i);
    if (c == '.') {
        depoisPonto = true;
    } else {
        int num = c - '0';
        if (!depoisPonto) {
            avaliacao = avaliacao * 10 + num;
        } else {
            avaliacao += num * decimal;
            decimal /= 10;
        }
    }
}
String tipos = categorias[5];
        int quantidadeTipos = 1;
        for (int i = 0; i < tipos.length(); i++) {
            if (tipos.charAt(i) == ';') {
                quantidadeTipos++;
            }
        }
        String[] tiposCozinha = new String[quantidadeTipos];
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
        int faixaPreco = categorias[6].length();
        String horario = categorias[7];
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
        Data dataAbertura = Data.parseData(categorias[8]);
        boolean aberto = false;
if (categorias[9].length() == 4 && categorias[9].charAt(0) == 't' && categorias[9].charAt(1) == 'r' && categorias[9].charAt(2) == 'u' && categorias[9].charAt(3) == 'e') {
    aberto = true;
}
        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }
    

    public String formatar() {
        String nova = "";
        nova += "[";
        nova += id + " ## ";
        nova += nome + " ## ";
        nova += cidade + " ## ";
        nova += capacidade + " ## ";
        nova += avaliacao + " ## ";
        nova += tiposCozinha + " ## ";
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

class ColecaoRestaurantes {
    int tamanho;
    Restaurante[] restaurantes; 
}

 public class RestaurantesMundo {
     public static void main(String[] args) {
         Scanner scanf = new Scanner(System.in);
         int n = scanf.nextInt();
         while (n !=-1) {
             formatar(n);
             n = scanf.nextInt();
         }
    }
}