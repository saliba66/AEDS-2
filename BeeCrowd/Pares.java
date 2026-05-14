import java.util.Scanner; 

class Pares {
    
    public static void swap(int[] array, int menor, int i) {
        int tmp = array[i];
        array[i] = array[menor];
        array[menor] = tmp;
    }
    

    public static void ordenar(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] % 2 == 0 && array[menor] % 2 != 0) {
                    menor = j;
                } else if (array[j] % 2 == 0 && array[menor] % 2 == 0) {
                    if (array[j] < array[menor]) {
                        menor = j;
                    }
                } else if (array[j] % 2 != 0 && array[menor] % 2 != 0) {
                        if (array[j] > array[menor]) {
                            menor = j;
                        }
                    }
                
            }
            swap(array, menor, i); 
    }
}


    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        int[] array = new int[n]; 
        for (int i = 0; i < n; i++) {
            array[i] = scanf.nextInt();
        }
        ordenar(array);
        for (int j = 0; j < array.length; j++) {
            System.out.println(array[j]); 
        }
}

}