import java.util.Scanner;

class Trem {
    int[] array;
    int n;

    Trem(int x) {
        array = new int[x];
        n = 0;
    }

    int remover() {
        if (n == 0) {
            return 0;
        }
        int resp = array[n - 1];
        n--;
        return resp;
    }

    void inserir(int x) {
        if (n >= array.length) {
            return;
        }
        array[n] = x;
        n++;
    }

    int topo() {
        if (n == 0) {
            return 0;
        }
        return array[n - 1];
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);

        int x = scanf.nextInt();
        Trem p = new Trem(x);

        int[] sequencia = new int[x];
        for (int i = 0; i < x; i++) {
            sequencia[i] = scanf.nextInt();
        }
        int proximo = 1;
        boolean ok = true;

        for (int i = 0; i < x; i++) {
            int desejado = sequencia[i];

            while ((p.n == 0 || p.topo() != desejado) && proximo <= x) {
                p.inserir(proximo);
                proximo++;
            }

            if (p.n > 0 && p.topo() == desejado) {
                p.remover();
            } else {
                ok = false;
                break;
            }

            if (ok) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }
}