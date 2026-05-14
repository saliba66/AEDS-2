import java.util.Scanner;

class Celula {
    public int elemento;
    public Celula prox;

    Celula(int x) {
        this.elemento = x;
        this.prox = null;
    }
}

class FilaLinear {
    private Celula primeiro;
    private Celula ultimo;


    void inserir(int x) {
        Celula nova = new Celula(x);
        ultimo.prox = nova;
        ultimo = nova;
    }

    int remover() {
        if (primeiro =ultimo) {
            return -1;
        }
    }
}