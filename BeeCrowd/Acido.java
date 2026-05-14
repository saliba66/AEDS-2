import java.util.*;

public class Acido {

    public static boolean combina(char a, char b) {
        return (a == 'B' && b == 'S') ||
               (a == 'S' && b == 'B') ||
               (a == 'C' && b == 'F') ||
               (a == 'F' && b == 'C');
    }

    public static int contarDobras(String s) {
        char[] pilha = new char[s.length()];
        int topo = -1;
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char atual = s.charAt(i);
            if (topo >= 0 && combina(pilha[topo], atual)) {
                topo--;
                count++;
            } else {
                topo++;
                pilha[topo] = atual;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String entrada = sc.next();
            int resp = contarDobras(entrada);
            System.out.println(resp);
        }

        sc.close();
    }
}