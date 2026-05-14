import java.util.Scanner; 

class Assasino {

    public static void swap(String[] assasinos, int menor, int maior) {
        String tmp = assasinos[menor];
        assasinos[menor] = assasinos[maior];
        assasinos[maior] = tmp;
    }

    public static void ordenar(String[] assasinos, int t) {
        for (int i = 0; i < t - 1; i++) {
            for (int j = 0; j < t - i - 1; j++) {
                if (assasinos[j].compareTo(assasinos[j + 1]) > 0) {
                    swap(assasinos, j, j + 1);
                }
            }
        }
    }

    public static String[] vivos1(String[] assasinos, String[] mortos, int t) {
        String[] vivosV = new String[t];
        int k = 0;
        for (int i = 0; i < t; i++) {
            boolean morreu = false;
            for (int j = 0; j < t; j++) {
                if (assasinos[i].compareTo(mortos[j]) == 0) {
                    morreu = true;
                    break;
                }
            }
            if (!morreu) {
                vivosV[k] = assasinos[i];
                k++;
            }
        }
        String[] vivos = new String[k];
        for (int i = 0; i < k; i++) {
            vivos[i] = vivosV[i];
        }
        return vivos;
    }
        
    public static void mortes(String[] assasinos, String[] v, int t) {
        for (int i = 0; i < v.length; i++) {
            int contador = 0;
            for (int j = 0; j < t; j++) {
                if (v[i].compareTo(assasinos[j]) == 0) {
                    contador++;
                }

            }
                 System.out.println(v[i] + contador);
            }  
        }
        
    

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String[] assasinos = new String[100];
        String[] mortos = new String[100];
        int tamanho = 0;
        int i = 0;
        while (i<6) {
            assasinos[i] = scanf.next();
            mortos[i] = scanf.next();
            i++;
            tamanho++;
        }
        ordenar(assasinos, tamanho);
        String[] v = vivos1(assasinos, mortos, tamanho);
        mortes(assasinos, v, tamanho);

    }

}