import java.util.Scanner; 

class Fila {
    int[] array;
    int n;
    
    Fila(int x) {
        array = new int[x];
        n = 0;
    }

    void inserirFinal(int x) {
        if (n >= array.length) {
            return;
        }
        array[n] = x;
        n++;
    }
    
    void removerInicio() {
        if (n == 0) {
            return;
        }
        for(int i = 0; i< n-1; i++){
            array[i] = array[i + 1]; 
        }
        n--;
    }
}