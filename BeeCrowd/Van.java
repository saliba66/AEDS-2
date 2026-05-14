import java.util.Scanner; 

class Van {
    String nome;
    int custo;
    char regiao;

    Van(String n, char r, int x) {
        nome = n;
        custo = x;
        regiao = r;
    }

    public static void swap(Van[] alunos, int menor, int i) {
        Van tmp = alunos[i];
        alunos[i] = alunos[menor];
        alunos[menor] = tmp;  
    }
    
    public static void ordenar(Van[] alunos) {
        for (int i = 0; i < alunos.length-1 ; i++) {
            int menor = i;
            for (int j = i + 1; j < alunos.length; j++) {
                if (alunos[menor].custo > alunos[j].custo) {
                    menor = j;
                }
                else if(alunos[menor].custo == alunos[j].custo){
                    if(alunos[menor].regiao > alunos[j].regiao){
                        menor = j ; 
                    }
                    else if(alunos[menor].regiao == alunos[j].regiao){
                        if(alunos[menor].nome.compareTo(alunos[j].nome) >0 ){
                            menor = j ; 
                        }
                    }
                }
            }
            swap(alunos, menor, i); 
        }
    }


    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        String nome;
        int custo;
        char regiao; 
    while(scanf.hasNext()){
        int y = scanf.nextInt();
        Van[] alunos = new Van[y];
        for (int i = 0; i < y; i++) {
            nome = scanf.next();
            regiao = scanf.next().charAt(0);
            custo = scanf.nextInt(); 
            alunos[i] = new Van(nome,regiao,custo);
        }
        ordenar(alunos);
        for (int j = 0; j < alunos.length; j++) {
            System.out.println(alunos[j].nome);
        }
    }
}

}