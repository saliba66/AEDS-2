import java.util.Scanner; 

class InversaoString {
    public static String inverter(String palavra1,int n){
        if (n == 0) {
            return " ";
        }
        return  palavra1.charAt(n-1) + inverter(palavra1, n-1);  // pega o caractere na ultima posicao e chama a funcao diminuindo o indice da palavra, quando n = 0, as chamadas voltam
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String palavra1 = scanf.nextLine();
        while (!(palavra1.length() == 3 && palavra1.charAt(0) == 'F' && palavra1.charAt(1) == 'I'
                && palavra1.charAt(2) == 'M')) {
                    int n = palavra1.length();    //passa o indice como int, para conseguir controlar ele na recursao
            System.out.println(inverter(palavra1, n));
            palavra1 = scanf.nextLine(); 
        }

    }
}