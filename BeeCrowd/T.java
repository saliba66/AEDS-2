import java.util.Scanner;

class T {
    int[] array;
    int n;

    T(int x) {
        array = new int[x];
        n = 0;
    }

    void inserir(int x) {
        if (n >= array.length) {
            return;
        }
        array[n] = x;
        n++;
    }

    void remover() {
        if (n == 0) {
            return;
        }
        n--;

    }

    int topo() {
        int topo = array[n - 1];
        return topo;
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        boolean verificar = true;
        while (n != 0) {
            int numero = 1;
            T pilha = new T(n);
            int[] x = new int[n];
            for (int j = 0; j < x.length; j++) {
                x[j] = scanf.nextInt();
            }
            for (int i = 0; i < n; i++) {
                while ((pilha.n == 0 || pilha.topo() != x[i]) && numero <= x.length) {
                    pilha.inserir(numero);
                    numero++;
                }
                if (pilha.topo() == x[i] && pilha.n > 0) {
                    pilha.remover();
                } else {
                    verificar = false;
                    break;
                }
            }
            if (verificar == true) {
                System.out.println("SIM");
            } else if (verificar == false) {
                System.out.println("NAO");
            }
            n = scanf.nextInt();
        }
    }
}