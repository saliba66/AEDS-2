import java.util.Scanner;

class Lista {
    int[] array;
    int n;

    Lista(int tamanho) {
        array = new int[tamanho];
        n = 0; 
    }

    void inserirInicio(int numero) {
        if (n >= array.length) {
            return;
        }
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = numero;
        n++;
    }

    void inserirFinal(int numero) {
        if (n >= array.length) {
            return;
        }
        array[n] = numero;
        n++;
    }

    void inserir(int numero, int posicao) {
        if (n >= array.length || posicao < 0 || posicao > n) {
            return;
        }
        for (int i = n; i > posicao; i--) {
            array[i] = array[i - 1];
        }
        array[posicao] = numero;
        n++;
    }
    
    int removerInicio() {
        if (n == 0) {
            return -1;
        }
        int resp = array[0];
        n--;
        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }
        return resp;
    }

    int removerFinal() {
        if (n == 0) {
            return -1;
        }
        n--;
        int resp = array[n];
        return resp;
    }

    int remover(int posicao) {
        if (n == 0 || posicao < 0 || posicao > n) {
            return -1;
        }
        int resp = array[posicao];
        for (int i = posicao; n > i; i++) {
            array[i] = array[i + 1];
        }
        n--;
        return resp; 
    }

   

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int x = scanf.nextInt();
        Lista[]lista = new Lista[x];
        for (int i = 0; i < x; i++) {
            int tamanho = scanf.nextInt();
            lista[i] = new Lista(tamanho); 
            int numero = scanf.nextInt();
            lista[i].inserirInicio(numero);
            lista[i].inserirFinal(numero);
            int p = scanf.nextInt();
            lista[i].inserir(numero, p);
            lista[i].removerInicio();
            lista[i].removerFinal();

        }
    }
        }

