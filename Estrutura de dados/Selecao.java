
import java.util.Scanner;
 
class Selecao {
    private static void swap(int x, int y, int[] sequencia) {
        int temp = sequencia[y];
        sequencia[y] = sequencia[x];
        sequencia[x] = temp;
    }

    public static void selecao(int[] sequencia) {
        int n = sequencia.length;
        for (int i = 0; i < n - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < n; j++) {
                if (sequencia[j] < sequencia[menor]) {
                    menor = j;
                }
            }
            swap(menor, i,sequencia);
        }
        for (int i = 0; i < n; i++) {
            System.out.print(sequencia[i]+" " );
    }
}

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int[] sequencia = new int[6];
        for (int i = 0; i < 6; i++) {
            sequencia[i] = scanf.nextInt();
        }
        selecao(sequencia);
        
        }
    }
