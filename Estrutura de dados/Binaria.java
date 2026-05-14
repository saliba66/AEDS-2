import java.util.Scanner;

class Binaria {
    public static boolean pesquisa(int[] numeros, int n) {
        boolean resp = false;
        int esquerda = 0;
        int direita = numeros.length - 1;
        while (esquerda <= direita) {
            int meio = (direita + esquerda) / 2;
            if (numeros[meio] == n) {
                resp = true;
                return resp; 
            }
            if (n < numeros[meio]) {
                direita = meio - 1;
            }
            if (n > numeros[meio]) {
                esquerda = meio + 1;
            }
        }
        return resp; 
    }

    public static void main(String args[]) {
        Scanner scanf = new Scanner(System.in);
        int[] sequencia = { 1, 2, 3, 4, 5 };

        int n = scanf.nextInt();
        
         System.out.print(pesquisa(sequencia, n));
    }
}