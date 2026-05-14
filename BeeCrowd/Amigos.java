import java.util.Scanner;

class Amigos {
    String nome;
    String opcao;

    Amigos(String n, String o) {
        nome = n;
        opcao = o;
    }

    public static String[] nomes(Amigos[] lista, int tamanho) {
        int novoTamanho = 1;
        for (int i = 0; i < tamanho-1; i++) {
            if (lista[i].nome.charAt(0) != lista[i + 1].nome.charAt(0)) {
                novoTamanho +=1;
            }
        }
        int k = 0;
        String[] nova = new String[novoTamanho];
        for (int i = 0; i < tamanho-1; i++) {
            if (lista[i].nome.charAt(0) != lista[i + 1].nome.charAt(0) && lista[i].nome.charAt(1) != lista[i + 1].nome.charAt(1)) {
                nova[k] = lista[i].nome;
                k++;
            } 
        }
        return nova;
    }

    public static void swap(Amigos[]lista,int i,int j){
        Amigos tmp = lista[i];
        lista[i] = lista[j];
        lista[j] = tmp;
    }

    public static void ordenar(Amigos[] lista, int tamanho) {
        String[] pessoas = new String[tamanho];
        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = 0; j < tamanho - i - 1; j++) {
                if (lista[j].opcao.charAt(0) == 'N' && lista[j + 1].opcao.charAt(0) == 'S') {
                    swap(lista, j, j + 1);
                } else if (lista[j].opcao.charAt(0) == lista[j + 1].opcao.charAt(0)) {
                    if (lista[j].nome.compareTo(lista[j + 1].nome) > 0) {
                        swap(lista, j, j + 1);
                    }
                }
            }
        }
        return;
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String nome = scanf.next();
        int i = 0;
        Amigos[] lista = new Amigos[100];
        while (!(nome.charAt(0) == 'F' && nome.charAt(1) == 'I' && nome.charAt(2) == 'M')) {
            String opcao = scanf.next();
            lista[i] = new Amigos(nome, opcao);
            i++;
            nome = scanf.next();
        }
        ordenar(lista, i);
        String[] nova = nomes(lista, i);
        for (int j = 0; j < nova.length; j++) {
            System.out.println(nova[j]);
        }
        
    }
    
}