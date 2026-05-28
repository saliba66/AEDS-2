import java.io.*;

//classe celula ------------------------------------------------------------------------------------------------------

class Celula {
    int numero;
    Celula cima, baixo, esq, dir;

    Celula() {
        this(0);
    }//end Celula

    Celula(int numero) {
        this.numero = numero;

        cima = baixo = esq = dir = null;
    }
}

//classe matriz ------------------------------------------------------------------------------------------------------

class Matriz {
    private Celula inicio;
    private int linha, coluna;

    Matriz(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;

        montarMatriz();
    }

    //metodo para montar a matriz encadeada
    private void montarMatriz() {
        inicio = new Celula();

        Celula inicioLinha = inicio;

        for (int i = 0; i < linha; i++) {
            Celula atual = inicioLinha;

            //cria as colunas
            for (int j = 1; j < coluna; j++) {
                Celula nova = new Celula();

                atual.dir = nova;
                nova.esq = atual;

                atual = nova;
            }

            //liga as linhas
            if (i < linha - 1) {
                Celula novaLinha = new Celula();

                inicioLinha.baixo = novaLinha;
                novaLinha.cima = inicioLinha;

                Celula superior = inicioLinha;
                Celula inferior = novaLinha;

                while (superior.dir != null) {
                    superior = superior.dir;

                    Celula nova = new Celula();

                    inferior.dir = nova;
                    nova.esq = inferior;

                    nova.cima = superior;
                    superior.baixo = nova;

                    inferior = nova;
                }

                inicioLinha = novaLinha;
            }
        }
    }

    //metodo para pegar uma celula especifica
    private Celula getPosicao(int l, int c) {
        Celula tmp = inicio;

        for (int i = 0; i < l; i++) {
            tmp = tmp.baixo;
        } //end for

        for (int j = 0; j < c; j++) {
            tmp = tmp.dir;
        } //end for

        return tmp;
    }//end getPosicao

    //setar elemento
    public void setNumero(int l, int c, int valor) {
        getPosicao(l, c).numero = valor;
    }//end setNumero

    //retornar elemento
    public int getNumero(int l, int c) {
        return getPosicao(l, c).numero;
    }//end getNumero

    //mostrar diagonal principal
    public void mostrarDiagonalPrincipal() {
        int limite;

        if (linha < coluna) {
            limite = linha;
        } else {
            limite = coluna;
        } //end if

        for (int i = 0; i < limite; i++) {

            if (i > 0) {
                System.out.print(" ");
            } //end if

            System.out.print(getNumero(i, i));
        } //end for

        System.out.println();
    }//end mostrarDiagonalPrincipal

    //mostrar diagonal secundaria
    public void mostrarDiagonalSecundaria() {
        int limite;

        if (linha < coluna) {
            limite = linha;
        } else {
            limite = coluna;
        } 

        for (int i = 0; i < limite; i++) {

            if (i > 0) {
                System.out.print(" ");
            } 

            System.out.print(getNumero(i, coluna - 1 - i));
        } 

        System.out.println();
    }

    //somar matrizes
    public Matriz soma(Matriz m) {
        Matriz resp = new Matriz(linha, coluna);

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {

                int valor = this.getNumero(i, j) + m.getNumero(i, j);

                resp.setNumero(i, j, valor);
            } 
        } 

        return resp;
    }

    //multiplicacao de matrizes
    public Matriz multiplicacao(Matriz m) {
        Matriz resp = new Matriz(this.linha, m.coluna);

        for (int i = 0; i < this.linha; i++) {
            for (int j = 0; j < m.coluna; j++) {

                int soma = 0;

                for (int k = 0; k < this.coluna; k++) {
                    soma += this.getNumero(i, k) * m.getNumero(k, j);
                } 

                resp.setNumero(i, j, soma);
            } 
        } //end for

        return resp;
    }

    //mostrar matriz
    public void mostrar() {
        for (int i = 0; i < linha; i++) {

            for (int j = 0; j < coluna; j++) {

                if (j > 0) {
                    System.out.print(" ");
                }

                System.out.print(getNumero(i, j));
            }

            System.out.println();
        }
    }
}

//scanner  -----------------------------------------------------------------------------------------------------

class Leitor {
    private final InputStream in = System.in;

    private final byte[] buffer = new byte[1 << 16];

    private int ponteiro = 0;
    private int tamanho = 0;

    private int ler() throws IOException {

        if (ponteiro >= tamanho) {
            tamanho = in.read(buffer);

            ponteiro = 0;

            if (tamanho <= 0) {
                return -1;
            }
        }

        return buffer[ponteiro++];
    }

    
    int nextInt() throws IOException {

        int c;

        do {
            c = ler();
        } while (c <= ' ' && c != -1);

        int sinal = 1;

        if (c == '-') {
            sinal = -1;
            c = ler();
        }

        int numero = 0;

        while (c > ' ') {
            numero = numero * 10 + (c - '0');

            c = ler();
        }

        return numero * sinal;
    }
}

//classe principal ---------------------------------------------------------------------------------------------------

public class Main {

    public static void main(String[] args) throws Exception {

        Leitor sc = new Leitor();

        int testes = sc.nextInt();

        for (int caso = 0; caso < testes; caso++) {

            int linha = sc.nextInt();
            int coluna = sc.nextInt();

            Matriz matriz1 = new Matriz(linha, coluna);

            //leitura primeira matriz
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {

                    matriz1.setNumero(i, j, sc.nextInt());
                }
            }

            Matriz matriz2 = new Matriz(linha, coluna);

            //leitura segunda matriz
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {

                    matriz2.setNumero(i, j, sc.nextInt());
                }
            }

            //mostrar diagonais
            matriz1.mostrarDiagonalPrincipal();
            matriz2.mostrarDiagonalSecundaria();

            //mostrar soma
            Matriz soma = matriz1.soma(matriz2);
            soma.mostrar();

            //mostrar multiplicacao
            Matriz mult = matriz1.multiplicacao(matriz2);
            mult.mostrar();
        }
    }
}/