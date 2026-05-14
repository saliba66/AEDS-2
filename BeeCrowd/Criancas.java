import java.util.Scanner;

class Criancas {
    char comp;
    String nome;

    Criancas(char x ,String n) {
        comp = x;
        nome = n;
    }

    public static void swap(Criancas[] lista, int menor, int maior) {
        Criancas tmp = lista[menor];
        lista[menor] = lista[maior];
        lista[maior] = tmp;
    }
    
    public static void ordenar(Criancas[] lista) {
        for (int i = 0; i < lista.length; i++) {
            for (int j = 0; j < lista.length - 1 - i; j++) {
                if (lista[j].nome.compareTo(lista[j + 1].nome) > 0) {
                    swap(lista, j, j + 1);
                }
            }
        }
    }
    
    public static int boa(Criancas[] lista) {
        int x = 0;
        for (int i = 0; i < lista.length; i++) {
            if (lista[i].comp == '+') {
                x++;
            }
        }
        return x;
    }
    
    public static int mal(Criancas[] lista) {
        int y = 0;
        for (int i = 0; i < lista.length; i++) {
            if (lista[i].comp == '-') {
                y++;
            }
        }
        return y;
    }

    
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        Criancas [] lista = new Criancas[n];
      for (int i = 0; i < n; i++) {
            char comp = scanf.next().charAt(0);
            String nome = scanf.next();        
            lista[i] = new Criancas(comp, nome);
        }
        ordenar(lista);
        int boas = boa(lista);
        int mas = mal(lista);
        for (int j = 0; j < lista.length; j++) {
            System.out.println(lista[j].nome);
    
        }
        System.out.println("Comportadas" + " " + boas  + " "+ "Mals" + " " +  mas);
            
        }

    }
