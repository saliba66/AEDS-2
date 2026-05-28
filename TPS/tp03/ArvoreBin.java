import java.io.*;
import java.util.*;

// classe data ------------------------------------------------------------------------------------------------------

class Data {
    private int dia, mes, ano;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public static Data parseData(String s) {
        String[] p = s.split("-");
        return new Data(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0]));
    }
}

// classe hora ------------------------------------------------------------------------------------------------------

class Hora {
    private int hora, minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public static Hora parseHora(String s) {
        String[] p = s.split(":");
        return new Hora(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }
}

// classe restaurante -----------------------------------------------------------------------------------------------

class Restaurante {
    private int id;
    private String nome;

    public Restaurante(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // transforma linha do csv em restaurante -----------------------------------------------------------------------

    public static Restaurante parseRestaurante(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useLocale(Locale.US);
        scanner.useDelimiter(",");

        int id = scanner.nextInt();
        String nome = scanner.next();

        scanner.close();

        return new Restaurante(id, nome);
    }
}

// colecao de restaurantes ------------------------------------------------------------------------------------------

class ColecaoRestaurantes {
    private Restaurante[] restaurantes;
    private int tamanho;

    // leitura do csv -----------------------------------------------------------------------------------------------

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

    // busca por id -------------------------------------------------------------------------------------------------

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
    No esq, dir;

    public No(Restaurante elemento) {
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }
}

// arvore binaria ---------------------------------------------------------------------------------------------------

class ArvoreBinaria {
    private No raiz;
    public long comparacoes = 0;

    public ArvoreBinaria() {
        raiz = null;
    }

    // inserir ------------------------------------------------------------------------------------------------------

    public void inserir(Restaurante x) {
        raiz = inserir(x, raiz);
    }

    private No inserir(Restaurante x, No i) {
        if (i == null) {
            i = new No(x);
        } else {
            comparacoes++;

            int cmp = x.getNome().compareTo(i.elemento.getNome());

            if (cmp < 0) {
                i.esq = inserir(x, i.esq);
            } else if (cmp > 0) {
                i.dir = inserir(x, i.dir);
            }
        }

        return i;
    }

    // pesquisar ----------------------------------------------------------------------------------------------------

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
}

// classe principal -------------------------------------------------------------------------------------------------

public class ArvoreBin {

    // verifica se uma string e numero ------------------------------------------------------------------------------

    public static boolean isNumero(String s) {
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
        ArvoreBinaria arvore = new ArvoreBinaria();

        ArrayList<String> entradas = new ArrayList<String>();

        // le todas as entradas -------------------------------------------------------------------------------------

        while (scanner.hasNextLine()) {
            entradas.add(scanner.nextLine());
        }

        int i = 0;

        // primeira parte: insere somente enquanto for numero -------------------------------------------------------

        while (i < entradas.size() && isNumero(entradas.get(i))) {
            int id = Integer.parseInt(entradas.get(i));

            Restaurante r = colecao.buscarPorId(id);

            if (r != null) {
                arvore.inserir(r);
            }

            i++;
        }

        // se tiver FIM depois dos ids, pula ------------------------------------------------------------------------

        if (i < entradas.size() && entradas.get(i).equals("FIM")) {
            i++;
        }

        // zera antes das pesquisas porque o log conta apenas as buscas --------------------------------------------

        arvore.comparacoes = 0;

        long inicio = System.nanoTime();

        // segunda parte: pesquisa os nomes -------------------------------------------------------------------------

        while (i < entradas.size()) {
            String nome = entradas.get(i);

            if (!nome.equals("FIM")) {
                boolean resp = arvore.pesquisar(nome);

                if (resp) {
                    System.out.println("SIM");
                } else {
                    System.out.println("NAO");
                }
            }

            i++;
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1000000.0;

        // arquivo log ----------------------------------------------------------------------------------------------

        FileWriter log = new FileWriter("885005_arvoreBinaria.txt");
        log.write("885005\t" + arvore.comparacoes + "\t" + tempo);
        log.close();

        scanner.close();
    }
}
