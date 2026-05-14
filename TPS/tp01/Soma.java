
import java.util.*;

public class Soma {
    public static int somar(int n) {
        if (n == 0) {
            return 0;
        } else
            return (n % 10) + somar(n / 10);   //retorna o ultimo digito + o proximo digito dividindo por 10
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numero;
        while (sc.hasNextInt()) {            
            numero = sc.nextInt();
            System.out.println(somar(numero));
        }
    }
}
