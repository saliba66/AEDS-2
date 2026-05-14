import java.util.Random;
import java.util.Scanner;

class Aleatoria {

    public static String alterar(String palavra, Random gerador) {
        char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));   //sorteia a letra que vai ser sorteada para ser substituida
        char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));    //sorteia a letra que vai substituir
        String resultado = "";
        for (int i = 0; i < palavra.length(); i++) {
            if (letra1 == palavra.charAt(i)) {                      //verifica se tem a letra sorteada na palavra, se tiver substituimos ela pela 2 letra sorteada
                resultado += letra2;                                    
            } else {
                resultado += palavra.charAt(i);                     // vamos criando a nova string assim
            }

        }
        return resultado;
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String palavra = scanf.nextLine();
        Random gerador = new Random();
        gerador.setSeed(4);

        while (!(palavra.length() == 3 && palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I'
                && palavra.charAt(2) == 'M')) {
            System.out.println(alterar(palavra, gerador));
            palavra = scanf.nextLine();
        }
        scanf.close();
    }

}
