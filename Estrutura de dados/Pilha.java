import java.util.Scanner;


class Pilha {
    int[] array;
    int n;
    
    Pilha(int tamanho) {
        array = new int[tamanho];
        n = 0;
    }
    
    void inserir(int x) {
        if (n >= array.length) {
            return; 
        }
        array[n] = x;
        n++;
    }

    int remover() {
        int resp = array[n - 1];
        n--;
        return resp;
    }

    void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.println(array[i]);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int tamanho = scanf.nextInt();
        Pilha presentes = new Pilha(tamanho);
        for (int i = 0; i < tamanho; i++) {
            int x = scanf.nextInt();
            presentes.inserir(x);
        }
        int z = presentes.remover();
         System.out.println("ELEMENTO REMOVIDO FOI :" + z);
        presentes.remover();
        presentes.remover();
        presentes.mostrar();
        
    }
}