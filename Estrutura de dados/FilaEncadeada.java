
class Celula {
    public int elemento;
    public Celula prox;

    Celula(int x) {
        this.elemento = x;
        this.prox = null;
    }
}

class FilaEncadeada {
    private Celula primeiro;
    private Celula ultimo;


    void inserir(int x) {
        Celula nova = new Celula(x);
        ultimo.prox = nova;
        ultimo = nova;
    }

    int remover() {
        if (primeiro == ultimo) {
            return -1;
        } else {
            int resp = primeiro.prox.elemento;
            primeiro.prox = primeiro.prox.prox;
            return resp;
        }
    }

    void mostrar() {
        Celula nova = primeiro.prox;
        while (nova != null) {
            System.out.println(nova.elemento);
            nova = nova.prox;
        }
    }
    
}