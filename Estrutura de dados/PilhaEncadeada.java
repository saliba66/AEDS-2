import java.util.Scanner;

class Celula {
    public int elemento;
    public Celula prox;
    
}


class PilhaEncadeada {
    private Celula topo;

    public static void inserir(int x) {
        Celula nova = new Celula(x);
        nova.prox = topo;
        topo = nova;
    }
    
    public static int remover() {
        if (topo == null) {
            return -1;
        }
        int resp = topo.elemento;
        topo = topo.prox;
        return resp;
    }

    public static void mostrar() {
        Celula atual = topo;
        while (atual != null) {
            System.out.println(atual.elemento);
            atual = atual.prox; 
        }
    }
    public static void main(String[] args) {
        
    }
}