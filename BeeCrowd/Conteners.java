import java.util.Scanner;

class Conteners {
    
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n;
        int limite;
        while (scanf.hasNext()) {
            n = scanf.nextInt();
            limite = scanf.nextInt();
            int[] atual = new int[n];
           
            for (int i = 0; i < atual.length; i++) {
                atual[i] = scanf.nextInt();
            }

            int[] removidos = new int[limite];
             int k = 0;
            for(int j = 0; j<n;j++){
                if(atual[j]%2 == 0 ){
                    removidos[k] = atual[j];
                    atual[j] = '0'; 
                    k++;
                    if(k == limite){
                        break;
                    }
                }
            }
            for (int l = 0; l < n && k<limite ; l++) {
                if (atual[l] % 2 == 1) {
                    removidos[k] = atual[l];
                    atual[l] = '0';
                    k++;
                    if (k == limite) {
                        break;
                    }
                }
            }
            int r = 0;
            int[] restantes = new int[n - limite];
            for (int m = 0; m < n  && k<limite ; m++) {
                if (atual[m] != '0') {
                    restantes[r] = atual[m];
                    r++;
                    atual[m] = '0';
                    if (r >= restantes.length) {
                        break;
                    }
                }
            }
            System.out.print("Descarregados :");
            for (int p = 0; p < removidos.length; p++) {
                System.out.print(" " + removidos[p]);
            }
            System.out.println();
             System.out.print("Restantes :");
            for(int q = 0; q<restantes.length;q++){
                System.out.print( " " + restantes[q]);
            }
                    System.out.println();
                }
                
            }
        }
    
