import java.util.Scanner;

class Camisetas {
    String cor;
    String tamanho;
    String nome;

    public static void swap(Camisetas[] alunos,int menor,int i) {
        Camisetas tmp = alunos[i];
        alunos[i] = alunos[menor];
        alunos[menor] = tmp; 
    }

    public static void ordenar(Camisetas[] alunos) {
        for (int i = 0; i < alunos.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < alunos.length; j++) {
                if (alunos[menor].cor.compareTo(alunos[j].cor) > 0) {
                    menor = j;
                } else if (alunos[menor].cor.compareTo(alunos[j].cor) == 0) {
                    if (alunos[menor].tamanho.compareTo(alunos[j].tamanho) < 0) {
                        menor = j;
                    } else if (alunos[menor].tamanho.compareTo(alunos[j].tamanho) == 0) {
                        if (alunos[menor].nome.compareTo(alunos[j].nome) > 0) {
                            menor = j;
                        }
                    }
                }
            }
            swap(alunos,menor,i);
        }
        for (int k = 0; k < alunos.length; k++) {
            System.out.println(alunos[k].tamanho + " " + alunos[k].cor + " " + alunos[k].nome); 
        }
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
          scanf.nextLine();
        while (n != 0) {
            Camisetas[] alunos = new Camisetas[n];
            for (int i = 0; i < alunos.length; i++) {
                alunos[i] = new Camisetas();
                alunos[i].nome = scanf.nextLine();
                alunos[i].cor = scanf.next();
                alunos[i].tamanho = scanf.next();
                scanf.nextLine(); 
            }
            ordenar(alunos);
            System.out.println(" ");
            n = scanf.nextInt();
            scanf.nextLine(); 
        }
    }

}