import java.util.Scanner;

class BubbleSort {

    public static void swap(int[] array, int menor, int maior) {
        int tmp = array[menor];
        array[menor] = array[maior];
        array[maior] = tmp;
}

public static void ordenar(int[] array) {
        for (int i = 0 ; i<array.length-1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
        for(int i = 0 ; i < array.length; i ++){
            System.out.println(array[i]); 
    }
}

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int tamanho = scanf.nextInt();
        int[] array = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = scanf.nextInt();
        }
        ordenar(array); 
    }
}