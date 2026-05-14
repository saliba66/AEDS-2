import java.util.Scanner;

public class Trilhos {
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);

        int v = scanf.nextInt();

        while (v != 0) {
            char[] vagao = new char[v];
            char[] ideal = new char[v];
            char[] pilha = new char[v];

            for (int i = 0; i < v; i++) {
                vagao[i] = scanf.next().charAt(0);
            }

            for (int i = 0; i < v; i++) {
                ideal[i] = scanf.next().charAt(0);
            }

            int topo = -1;
            int i = 0, j = 0;
            String ops = "";

            while (i < v) {
                pilha[++topo] = vagao[i++];
                ops += "I";

                while (topo >= 0 && j < v && pilha[topo] == ideal[j]) {
                    topo--;
                    j++;
                    ops += "R";
                }
            }

            if (j == v) {
                System.out.println(ops);
            } else {
                System.out.println(ops + " Impossible");
            }

            v = scanf.nextInt();
        }

        scanf.close();
    }
}