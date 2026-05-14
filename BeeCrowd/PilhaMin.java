import java.util.Scanner; 

class PilhaMin {
    int[] array;
    int n = 0;
    PilhaMin(int tamanho) {
        array = new int[tamanho];
        n = 0;
    }
    void push(int numero) {
        array[n] = numero;
        n++;
    }
    void pop() {
        if (n > 0) {
            n--;
        }
    }
    int min() {
        if (n > 0) {
            int menor = array[0];
            for (int i = 1; i < n; i++) {
                if (menor > array[i]) {
                    menor = array[i];
                }
            }
            return menor;
        }
        return 0;
    }
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int operacoes = scanf.nextInt();
        PilhaMin pilha = new PilhaMin(operacoes);
        for (int i = 0; i < operacoes; i++) {
            String comando = scanf.next();
            if (comando.charAt(0) == 'P' && comando.charAt(1) == 'U') {
                int numero = scanf.nextInt();
                pilha.push(numero);
            }
            else if (comando.charAt(0) == 'P' && comando.charAt(1) == 'O') {
                pilha.pop();
            }
            else if (comando.charAt(0) == 'M' && comando.charAt(1) == 'I') {
                int menor = pilha.min();
                System.out.println(menor);
            }
        }
    }
}