import java.util.ArrayList;
import java.util.Scanner;

// celula da matriz ------------------------------------------------------------------------------------------------

class Celula {
    public int elemento;
    public Celula sup;
    public Celula inf;
    public Celula esq;
    public Celula dir;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.sup = null;
        this.inf = null;
        this.esq = null;
        this.dir = null;
    }
}

// matriz dinamica -------------------------------------------------------------------------------------------------

class Matriz {
    private Celula inicio;
    private int linhas;
    private int colunas;

    // construtor --------------------------------------------------------------------------------------------------

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;

        Celula[][] grade = new Celula[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                grade[i][j] = new Celula();
            }
        }

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (i > 0) {
                    grade[i][j].sup = grade[i - 1][j];
                }

                if (i < linhas - 1) {
                    grade[i][j].inf = grade[i + 1][j];
                }

                if (j > 0) {
                    grade[i][j].esq = grade[i][j - 1];
                }

                if (j < colunas - 1) {
                    grade[i][j].dir = grade[i][j + 1];
                }
            }
        }

        inicio = grade[0][0];
    }

    // pegar celula ------------------------------------------------------------------------------------------------

    private Celula getCelula(int linha, int coluna) {
        Celula atual = inicio;

        for (int i = 0; i < linha; i++) {
            atual = atual.inf;
        }

        for (int j = 0; j < coluna; j++) {
            atual = atual.dir;
        }

        return atual;
    }

    // set ---------------------------------------------------------------------------------------------------------

    public void setElemento(int linha, int coluna, int valor) {
        getCelula(linha, coluna).elemento = valor;
    }

    // get ---------------------------------------------------------------------------------------------------------

    public int getElemento(int linha, int coluna) {
        return getCelula(linha, coluna).elemento;
    }

    // ler ---------------------------------------------------------------------------------------------------------

    public void ler(ArrayList<Integer> dados, int[] pos) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                setElemento(i, j, dados.get(pos[0]++));
            }
        }
    }

    // diagonal principal ------------------------------------------------------------------------------------------

    public void mostrarDiagonalPrincipal() {
        Celula atual = inicio;

        for (int i = 0; i < linhas && i < colunas; i++) {
            System.out.print(atual.elemento);

            if (i < Math.min(linhas, colunas) - 1) {
                System.out.print(" ");
            }

            if (atual.dir != null && atual.inf != null) {
                atual = atual.dir.inf;
            }
        }

        System.out.println();
    }

    // diagonal secundaria -----------------------------------------------------------------------------------------

    public void mostrarDiagonalSecundaria() {
        Celula atual = inicio;

        while (atual.dir != null) {
            atual = atual.dir;
        }

        for (int i = 0; i < linhas && i < colunas; i++) {
            System.out.print(atual.elemento);

            if (i < Math.min(linhas, colunas) - 1) {
                System.out.print(" ");
            }

            if (atual.esq != null && atual.inf != null) {
                atual = atual.esq.inf;
            }
        }

        System.out.println();
    }

    // soma --------------------------------------------------------------------------------------------------------

    public Matriz somar(Matriz outra) {
        Matriz resp = new Matriz(linhas, colunas);

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resp.setElemento(i, j, getElemento(i, j) + outra.getElemento(i, j));
            }
        }

        return resp;
    }

    // multiplicacao -----------------------------------------------------------------------------------------------

    public Matriz multiplicar(Matriz outra) {
        Matriz resp = new Matriz(linhas, outra.colunas);

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < outra.colunas; j++) {
                int soma = 0;

                for (int k = 0; k < colunas; k++) {
                    soma += getElemento(i, k) * outra.getElemento(k, j);
                }

                resp.setElemento(i, j, soma);
            }
        }

        return resp;
    }

    // mostrar -----------------------------------------------------------------------------------------------------

    public void mostrar() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(getElemento(i, j));

                if (j < colunas - 1) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }
}

// classe principal ------------------------------------------------------------------------------------------------

public class MatrizDinamica {

    public static boolean consegueLerCaso(ArrayList<Integer> dados, int[] pos) {
        if (pos[0] + 1 >= dados.size()) {
            return false;
        }

        int linhas1 = dados.get(pos[0]++);
        int colunas1 = dados.get(pos[0]++);

        if (linhas1 <= 0 || colunas1 <= 0) {
            return false;
        }

        if (pos[0] + (linhas1 * colunas1) > dados.size()) {
            return false;
        }

        pos[0] += linhas1 * colunas1;

        if (pos[0] + 1 >= dados.size()) {
            return false;
        }

        int linhas2 = dados.get(pos[0]++);
        int colunas2 = dados.get(pos[0]++);

        if (linhas2 <= 0 || colunas2 <= 0) {
            return false;
        }

        if (pos[0] + (linhas2 * colunas2) > dados.size()) {
            return false;
        }

        pos[0] += linhas2 * colunas2;

        return true;
    }

    public static void processarCaso(ArrayList<Integer> dados, int[] pos) {
        int linhas1 = dados.get(pos[0]++);
        int colunas1 = dados.get(pos[0]++);

        Matriz matriz1 = new Matriz(linhas1, colunas1);
        matriz1.ler(dados, pos);

        int linhas2 = dados.get(pos[0]++);
        int colunas2 = dados.get(pos[0]++);

        Matriz matriz2 = new Matriz(linhas2, colunas2);
        matriz2.ler(dados, pos);

        matriz1.mostrarDiagonalPrincipal();
        matriz2.mostrarDiagonalSecundaria();
        matriz1.somar(matriz2).mostrar();
        matriz1.multiplicar(matriz2).mostrar();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> dados = new ArrayList<Integer>();

        while (scanner.hasNextInt()) {
            dados.add(scanner.nextInt());
        }

        if (dados.size() == 0) {
            scanner.close();
            return;
        }

        int primeiro = dados.get(0);
        boolean temQuantidadeCasos = false;

        if (primeiro > 0) {
            int[] teste = new int[] {1};
            temQuantidadeCasos = true;

            for (int caso = 0; caso < primeiro; caso++) {
                if (!consegueLerCaso(dados, teste)) {
                    temQuantidadeCasos = false;
                    break;
                }
            }

            if (temQuantidadeCasos && teste[0] != dados.size()) {
                temQuantidadeCasos = false;
            }
        }

        if (temQuantidadeCasos) {
            int[] pos = new int[] {1};

            for (int caso = 0; caso < primeiro; caso++) {
                processarCaso(dados, pos);
            }
        } else {
            int[] pos = new int[] {0};

            while (consegueLerCaso(dados, new int[] {pos[0]})) {
                processarCaso(dados, pos);
            }
        }

        scanner.close();
    }
}
