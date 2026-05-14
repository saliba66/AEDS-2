import java.util.Scanner; 

class Cartas {
    int[] array;
    int n;

    Cartas(int x) {
        array = new int[n];
        n = 0;

    }

    int removerInicio() {
        if (n == 0) {
            return -1;
        }
        int resp = array[0];
        n--;
        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }
        return resp;
    }
    
    void inserirFim(int x) {
        array[n] = x;
        n++;
    }

    void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.println(array[i]);
        }
        
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int x = scanf.nextInt();
        while (x != 0) {
            Cartas lista = new Cartas(x);
            Cartas descartadas = new Cartas(x);
            for (int i = 1; i <= x; i++) {
                lista.inserirFim(i);
            }
            while (lista.n > 1) {
                descartadas.inserirFim(lista.removerInicio());
                lista.inserirFim(lista.removerInicio());
            }
            descartadas.mostrar();
            System.out.println("Discarded cards:");
            descartadas.mostrar();
            System.out.print("Restantes");
            lista.mostrar();
             x = scanf.nextInt();
        }
    }


    




    }