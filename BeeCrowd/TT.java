import java.util.Scanner;

class TT {
    char[] array;
    int n;

    TT(int tamanho) {
        array = new char[tamanho];
        n = 0;
    }

    void inserir(char x) {
        if (n < array.length) {
            array[n] = x;
            n++;
        }
    }

    char remover() {
        if (n == 0) {
            return '\0';
        }
        n--;
        return array[n];
    }

    char topo() {
        if (n == 0) {
            return '\0';
        }
        return array[n - 1];
    }

    boolean vazia() {
        return n == 0;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        while (n != 0) {
            char[] entrada = new char[n];
            char[] saida = new char[n];

            for (int i = 0; i < n; i++) {
                entrada[i] = sc.next().charAt(0);
            }

            for (int i = 0; i < n; i++) {
                saida[i] = sc.next().charAt(0);
            }

            TT pilha = new TT(n);
            int j = 0;

            for (int i = 0; i < n; i++) {
                pilha.inserir(entrada[i]);
                System.out.print("I");
                while (!pilha.vazia() && j < n && pilha.topo() == saida[j]) {
                    pilha.remover();
                    System.out.print("R");
                    j++;
                }
            }

            if (j == n) {
                System.out.println();
            } else {
                System.out.println(" Impossible");
            }

            n = sc.nextInt();
        }

        sc.close();
    }
}