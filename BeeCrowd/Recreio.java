import java.util.Scanner;

class Recreio {
     
    public static void swap(int[]fila , int j, int i){
        int tmp = fila[i];
        fila[i] = fila[j];
        fila[j] = tmp;   
    }

    public static int[]nova( int[] fila ){
        int[] antes = new int[fila.length];
        for(int i = 0; i<fila.length; i++){
            antes[i] = fila[i];
        }
        return antes; 
    }


        public static void ordenar(int[]fila){
            for(int i = 0; i<fila.length - 1 ; i++){
                for(int j = 0 ; j<fila.length-1 -i; j++){
                    if(fila[j]<fila[j+1]){
                        swap(fila,j,j+1); 
                    }
                }
            }
        }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        for (int i = 0; i < n; i++) {
            int mudancas = 0;
            int tamanho = scanf.nextInt();
                int[]fila = new int [tamanho];
                for (int j = 0; j < tamanho; j++) {
                    fila[j] = scanf.nextInt();
                }
                int[]antes =  nova(fila); 
                ordenar(fila); 
                for (int k = 0; k < fila.length; k++) {
                    if (antes[k] == fila[k]) {
                        mudancas += 1;
                    }
                }
                System.out.println(mudancas); 
            }
        }

    }