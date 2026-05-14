import java.util.Scanner;

class SomaDigitos {

    public static int somar(String s, int i) {
        // Caso base: quando chegar no final da string
        if (i == s.length()) {
            return 0;
        }
        // Converte o caractere para número
        // Ex: '5' - '0' = 5
        int valor = s.charAt(i) - '0';

        // Soma o valor atual com o resto da string
        return valor + somar(s, i + 1);
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String x;

        x = scanf.next();
        while (!(x.length() >= 3 &&
                 x.charAt(0) == 'F' && x.charAt(1) == 'I' && x.charAt(2) == 'M')) {
            System.out.println(somar(x, 0));
            x = scanf.next();
        }
    }
}