import java.util.Scanner; 

class Elfo {
    String nome;
    int peso;
    int idade;
    double altura;
    

    public static void swap(Elfo[] renas, int maior, int i) {
        Elfo tmp = renas[i];
        renas[i] = renas[maior];
        renas[maior] = tmp;
    }

    public static void selecionar(Elfo[]renas , int quantidade){
        for (int i = 0; i < quantidade; i++) {
            System.out.println( i+1 + " - " + renas[i].nome); 
        }
    }

    public static void ordenar(Elfo[]renas,int quantidade){
        for (int i = 0; i < renas.length - 1; i++) {
            int maior = i;
            for (int j = 1 + i; j < renas.length; j++) {
                if (renas[maior].peso < renas[j].peso) {
                    maior = j;
                } else if (renas[maior].peso == renas[j].peso) {
                    if (renas[maior].idade > renas[j].idade) {
                        maior = j;
                    } else if (renas[maior].idade == renas[j].idade) {
                        if (renas[maior].altura > renas[j].altura) {
                            maior = j;
                        } else if (renas[maior].altura == renas[j].altura) {
                            if (renas[maior].nome.compareTo(renas[j].nome) > 0) {
                                maior = j;
                            }
                        }
                    }
                }
            }
            swap(renas, maior, i);
        }
        selecionar(renas, quantidade); 
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in).useLocale(java.util.Locale.US);
         int n = 0;
        int casos = scanf.nextInt();
        while (n < casos) {
         int x = scanf.nextInt();
         int quantidade = scanf.nextInt();
            Elfo[] renas = new Elfo[x]; 
            for (int i = 0; i < x; i++) {
                renas[i] = new Elfo();
                renas[i].nome = scanf.next();
                renas[i].peso = scanf.nextInt();
                renas[i].idade = scanf.nextInt();
                renas[i].altura = scanf.nextDouble();
            }
            System.out.println("CENARIO {"+ (n +1 ) +"}"); 
            ordenar(renas, quantidade);
            n += 1; 
        }
    }
}