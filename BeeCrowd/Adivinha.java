import java.util.Scanner;

class Adivinha {
    public static int verificar(int[] array, int sorteado) {
        int proximo = 100;
        int indice = 0; 
        for (int i = 0; i < array.length; i++) {
            if (array[i] == sorteado) {
                return i + 1;
            } else {
                int teste = array[i] - sorteado;
                if (teste < 0) {
                    teste = -teste;
                }
                if (teste < proximo) {
                    proximo = teste;
                    indice = i;
                }
            }
        }
            return indice + 1;
        }
        
            


    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int sorteios = scanf.nextInt();
        int escolhido;
        for (int i = 0; i < sorteios; i++) {
            int alunos = scanf.nextInt();
            int sorteado = scanf.nextInt();
            int[] array = new int[alunos];
            for (int j = 0; j < alunos; j++) {
                array[j] = scanf.nextInt();
            }
            escolhido = verificar(array, sorteado);
            System.out.println(escolhido);
        }
    }
}