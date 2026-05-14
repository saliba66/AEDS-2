import java.util.Scanner;

class CartasB {
    public static int[] embaralhar(int[] ordem) {
        int[] metade1 = new int[ordem.length / 2];
        int[] metade2 = new int[metade1.length];
        int[] nova = new int[ordem.length];
        for (int i = 0; i < metade1.length; i++) {
            metade1[i] = ordem[i];
        }
        int ex = 0;
        for (int j = ordem.length/2; j < ordem.length; j++) {
            metade2[ex] = ordem[j];
            ex++;
        }
        int aux = 0;
        for (int k = 0; k < metade1.length; k++) {
            nova[k + aux] = metade2[k];
            nova[k + 1 + aux] = metade1[k];
            aux++;
        }
        return nova;
    }
    
    public static boolean comparar(int[] nova, int[] ordem) {
        for (int i = 0; i < ordem.length; i++) {
            if (nova[i] != ordem[i]) {
                return false;
                
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        int tamanho = 2 * n;
        int[] ordem = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            ordem[i] = i;
        }
        int[] nova = embaralhar(ordem);
        int cont = 1;
        
        while(!comparar(nova,ordem)){
            nova = embaralhar(nova);
            cont ++;
        }
        System.out.println(cont);
    }
}