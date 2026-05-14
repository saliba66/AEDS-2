import java.util.Scanner; 

class OrdencaoPorTamanho {

    public static void swap(int maior, int i, String[] palavras) {
        String tmp = palavras[i];
        palavras[i] = palavras[maior];
        palavras[maior] = tmp;
    }

    public static void ordernar(String[] palavras) {
        for (int i = 0; i < palavras.length - 1; i++) {
            int maior = i;
            for (int j = i + 1; j < palavras.length; j++) {
                if (palavras[j].length() > palavras[maior].length()) {
                    maior = j;
                }
            }
            swap(maior, i, palavras);
        }
        
    }

    public static void dividir(String linha) {
        String[] palavras = linha.split(" ");
        ordernar(palavras);
        for (int k = 0; k < palavras.length; k++) {
            System.out.print(palavras[k] + " ");
        }
        System.out.println();
              //System.out.print(palavras.length);

    }
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        scanf.nextLine();
        for (int i = 0; i < n; i++) {
            String linha = scanf.nextLine();
            dividir(linha);
        }

    }

}

