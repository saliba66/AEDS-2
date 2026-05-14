import java.util.Scanner;

class Duendes {
    String nome;
    int idade;

    Duendes(String n, int i) {
        nome = n;
        idade = i;
    }

    public static void swap(Duendes[] time, int menor, int maior) {
        Duendes tmp = time[menor];
        time[menor] = time[maior];
        time[maior] = tmp;
    }

    public static void times(Duendes[] time) {
        int quantidade = time.length / 3;
        int j = 0;
        int i = 0;
        while (quantidade > j) {
            System.out.print("TIME" + (j + 1));
            System.out.println();
            System.out.println(time[j].nome  + " " + time[j].idade);
            System.out.println(time[j + quantidade].nome  + " "  + time[j + quantidade].idade);
            System.out.println(time[j + quantidade * 2].nome + " " + time[j + quantidade * 2].idade);
            System.out.println();
            j++;
                }
    }
    
    public static void ordenar(Duendes[] time) {
        for (int i = 0; i < time.length - 1; i++) {
            for (int j = 0; j < time.length - i - 1; j++) {
                if (time[j].idade < time[j + 1].idade) {
                    swap(time, j, j + 1);
                } else if (time[j].idade == time[j + 1].idade) {
                    if (time[j].nome.compareTo(time[j + 1].nome) > 0) {
                        swap(time, j, j + 1);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int x = scanf.nextInt();
        Duendes[] time = new Duendes[x];
        for (int i = 0; i < x; i++) {
            String nome = scanf.next();
            int idade = scanf.nextInt();
            time[i] = new Duendes(nome, idade);
        }
        ordenar(time);
        times(time);
    }
}