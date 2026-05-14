
import java.util.Scanner;

public class Cesar {
    public static String proximo(String palavra) {
        String resultado = ""; // criamos uma string vazia e iremos preecher ela, para representar nossa
                               // resposta, imprindo ela e retornando como o problema pede
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) >= 32 && palavra.charAt(i) <= 122) { // verifica se o elemento da string é um
                                                                       // caractere especial ou nao, caso seja ele cai
                                                                       // no else e continua o mesmo, se for um
                                                                       // caractere normal somamos +3 a ele, como o
                                                                       // problema nos pede e fazemos a conversao de
                                                                       // inteiros para letras depois
                resultado += (char) (palavra.charAt(i) + 3);
            } else {
                resultado += (char) (palavra.charAt(i));
            } // preenchemos a nova string avancando 3 aos caracteres da string
              // recebida,formamos uma nova string com os novos caracteres
        }
        System.out.println(resultado);
        return resultado;
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        String texto = entrada.nextLine();
        while (!(texto.length() == 3 && texto.charAt(0) == 'F' && texto.charAt(1) == 'I' && texto.charAt(2) == 'M')) { // nossa
                                                                                                                       // condicao
                                                                                                                       // de
                                                                                                                       // parada
                                                                                                                       // do
                                                                                                                       // while
                                                                                                                       // que
                                                                                                                       // roda
                                                                                                                       // quando
                                                                                                                       // a
                                                                                                                       // string
                                                                                                                       // recebida
                                                                                                                       // é
                                                                                                                       // diferente
                                                                                                                       // da
                                                                                                                       // palavra
                                                                                                                       // FIM
            proximo(texto); // chamamos nosso metodo enviando a string digitada, e após isso armazena uma
                            // nova palavra e repete todo o processo do while
            texto = entrada.nextLine();
        }

        entrada.close();
    }
}
