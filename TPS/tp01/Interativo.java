import java.util.Scanner;

class Interativo {
    public static boolean vogal(String palavra) {
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) != 'A' && palavra.charAt(i) != 'E' && palavra.charAt(i) != 'I'          //verifica se o caractere na posição i é diferente de alguma letra vogal, incluindo maisculas e minusculas "A"e "a" por exemplo
                     && palavra.charAt(i) != 'O' && palavra.charAt(i) != 'U' &&
                    palavra.charAt(i) != 'a' && palavra.charAt(i) != 'e' && palavra.charAt(i) != 'i'
                    && palavra.charAt(i) != 'o' && palavra.charAt(i) != 'u') {
                return false;
            }
        }

        return true;
    }
    public static boolean consoantes(String palavra) {
        for (int i = 0; i < palavra.length(); i++) {
           if (palavra.charAt(i) >= '0' && palavra.charAt(i) <= '9') {                              //verifica tambem se tem algum número como caracter, sem isso o metodo retornaria true para uma String 123 por exemplo, já que nenhum elemento é uma vogal
                return false;       
            }
            if (palavra.charAt(i) == 'A' || palavra.charAt(i) == 'E' || palavra.charAt(i) == 'I'              //verifica se algum dos caracteres é vogal, se for o método retorna falso
                    || palavra.charAt(i) == 'O' || palavra.charAt(i) == 'U' ||
                    palavra.charAt(i) == 'a' || palavra.charAt(i) == 'e' || palavra.charAt(i) == 'i'
                    || palavra.charAt(i) == 'o' || palavra.charAt(i) == 'u') {
                return false;
            }
        }
        return true;
    }
    public static boolean inteiro(String palavra) {
        for (int i = 0; i < palavra.length(); i++) {
            if (!(palavra.charAt(i) >= '0' && palavra.charAt(i) <= '9')) {           //verifica se tem algo diferente de numeros como caracteres
                return false;    
            }
        }
        return true;
    }
    public static boolean real(String palavra) {
        int virgula = 0;
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == ',' || palavra.charAt(i) == '.') {            //faz um contador para , ou . já que um número 12,2,3 nao é considerado real
                virgula += 1;  
            }
            if (virgula > 1) {      
                return false;
            }
            if ((palavra.charAt(i) >= 'a' && palavra.charAt(i) <= 'z' || palavra.charAt(i)>= 'A' && palavra.charAt(i)<='Z')) {       //verifica se os caracteres da string sao letras, se colocasse spara verificar se são numeros, algo como 12,4 daria como falso, por causa da vírgula
                return false;

            }
        }
        return true; 
    }
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String palavra = scanf.nextLine();
        while (!(palavra.length() == 3 && palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I'
                && palavra.charAt(2) == 'M')) {
            System.out.print(vogal(palavra) ? "SIM " : "NAO ");
            System.out.print(consoantes(palavra) ? "SIM " : "NAO ");            //basicamente um if else na hora de printar, chama a função if true printa SIM, else printa NAO
            System.out.print(inteiro(palavra) ? "SIM " : "NAO ");
            System.out.println(real(palavra) ? "SIM" : "NAO");
            palavra = scanf.nextLine();
            
        }
    }
}