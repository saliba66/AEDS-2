import java.util.Scanner;

class Arvores {
    public static void swap(String[] arvores, int menor, int maior) {
        String tmp = arvores[menor];
        arvores[menor] = arvores[maior];
        arvores[maior] = tmp;
    }

    public static void ordenar(String[] arvores) {
        for (int i = 0; i < arvores.length; i++) {
            for (int j = 0; j < arvores.length - 1 - i; j++) {
                if (arvores[j].compareTo(arvores[j + 1]) > 0) {
                    swap(arvores, j, j + 1);
                }
            }
        }
    }

    public static void porcentagem(String[] arvores) {
        int[] contador = new int[arvores.length]; 
        for (int i = 1; i < arvores.length - 1; i++) {
            if (arvores[i].compareTo(arvores[i - 1]) == 0) {
                contador[i] ++;
        }
    }

}
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String[] arvores = new String[1000];
        int n = scanf.nextInt();
        int x = 0;
        while (x < n) {
            int i = 0;
            arvores[i] = scanf.nextLine(); 
            while (arvores[i] != "") {
                i++;
                arvores[i] = scanf.nextLine();
            }
            ordenar(arvores); 
        }
    }
}