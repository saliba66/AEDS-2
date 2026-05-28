import java.util.Scanner;

class Tamanho {
    public static void swap(String[] frase, int i, int j) {
        String tmp = frase[i];
        frase[i] = frase[j];
        frase[j] = tmp;
    }

    public static void ordenar(String[]frase, int tamanho){
        for(int i = 0; i<tamanho -1; i++){
            for(int j = 0; j< tamanho -1 - i;j++){
                if(frase[j].length()<frase[j+1].length()){
                    swap(frase,j,j+1);
                }
        }

    public static void main(String[]args){
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        int i = 0;
        while(i<n){
            String[]frase = new String[50];
            String linha = scanf.nextLine();
            String palavra = "";
            int pos = 0;
            for(int i = 0; i<palavra.length(); i++){
                if(linha.charAt(j) != ''){
                    palavra+= linha.charAt(j);
                }else{
                    frase[pos] = palavra;
                    pos++;
                    palavra ="";
                }
            }
           frase[pos] = palavra;
           ordenar(frase,pos+1);
           for (int j = 0; j <= pos; j++) {
    System.out.print(frase[j]);

    if (j < pos) {
        System.out.print(" ");
    }
           }
    System.out.println();
            i++;
        }
    }
}

