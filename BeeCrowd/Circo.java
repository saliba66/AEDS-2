import java.util.Scanner;

class Circo {
    public static int calcular(int valores[], int custo) {
        int melhor = 0;
        int atual = 0;
        for (int i = 0; i < valores.length; i++) {
            int lucro = valores[i] - custo;
            atual = atual + lucro;
            if (atual < 0) {
                atual = 0;
            }

            if (atual > melhor) {
                melhor = atual;
            }
        }
        return melhor;
    }

    public static void main(String args[]) {

        Scanner scanf = new Scanner(System.in);
        while (scanf.hasNext()) {
            int dias = scanf.nextInt();
            int custo = scanf.nextInt();
            int valores[] = new int[dias];
            int resposta;
            for (int i = 0; i < dias; i++) {
                valores[i] = scanf.nextInt();
            }
            resposta = calcular(valores, custo);
            System.out.println(resposta);
        }
    }
}

// estudar mais a logica desse codigo, na parte do lucro diario