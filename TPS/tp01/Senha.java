import java.util.Scanner;

class Senha {

    public static boolean verificar(String senha) {
        int minuscula = 0;                                  //faz contadores para cada caracter necessário para ser uma senha forte, numeros,maisucula,minuscula e especial
        int maiscula = 0; 
        int numeros = 0;
        int especial = 0; 
        for (int i = 0; i < senha.length(); i++) {                           //verifica se tem 8 caracteres minimo, já discarta se nao tiver
            if (senha.length() < 8) {
                return false;
            } else if (senha.charAt(i) >= 'a' && senha.charAt(i) <= 'z') {    //verifica se tem alguma minuscula, e adiciona ao contador se achar
                minuscula++;
            }
            else if(senha.charAt(i)>= 'A' && senha.charAt(i)<= 'Z'){            //mesma coisa so que com maiscula
                maiscula++; 
            }
            else if(senha.charAt(i) >= '0' && senha.charAt(i)<='9'){            //verifica numeros
                numeros ++; 
            }
            else if (!(senha.charAt(i) >= 'A' && senha.charAt(i) <= 'Z' || //verifica especiais que sao caracteres diferentes de numeros e letras maisculas e minusculas ;
            senha.charAt(i)>='a' && senha.charAt(i)<='z' ||
            senha.charAt(i)>= '0' && senha.charAt(i)<='9')){
                especial++;
            }
        }

            if( minuscula == 0 || maiscula == 0 || numeros == 0 ||especial == 0 ){      //verifica os contadores, se algum for 0, retorna false, mostrando que a senha é fraca
                return false; 
            }
            else{
                return true;
            }
        }
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String senha = scanf.nextLine();
        while(!(senha.length() == 3 && senha.charAt(0) == 'F' && senha.charAt(1) == 'I'
                && senha.charAt(2) == 'M')) {
            if (verificar(senha) == false) {
                System.out.println("NAO");
            }
            else {
                System.out.println("SIM");
            }
            senha = scanf.nextLine(); 
                }
                

    }
}