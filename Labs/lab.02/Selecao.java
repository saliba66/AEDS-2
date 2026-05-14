 import java.util.Scanner;
  
 class Selecao {

     public static int swap(int menor, int i, int[] array,int trocas) {
         int tmp = array[i];
         array[i] = array[menor];
         array[menor] = tmp;
         trocas += 1;
         return trocas; 
     }

     public static void crescente(int n, int[] array) {
         for (int i = 0; i < n; i++) {
             array[i] = i;
         }
         ordenar(n, array);
     }

     public static void decrescente(int n, int[] array) {
         for (int i = 0; i<n; i++) {
             array[i] = n-i-1;
         }
         ordenar(n, array); 
     }

     public static void ordenar(int n, int[] array) {
         int comparacao = 0;
         int trocas = 0;
         for (int i = 0; i < n - 1; i++) {
             int menor = i;
             for (int j = i + 1; j < n; j++) {
                 if (array[j] < array[menor]) {
                     menor = j;
                 }
                 comparacao += 1;
             }
             if (menor != i) {
                 trocas = swap(menor, i, array, trocas);
             }
         }
         System.out.println(trocas);
     }

     public static void main(String[] args) {
         Scanner scanf = new Scanner(System.in);
         int n = scanf.nextInt();
         int[] array = new int[n];
         decrescente(n, array);
        //crescente(n, array);
         for (int i = 0; i < n; i++) {
             System.out.print(array[i]); 
         }
     }
 }