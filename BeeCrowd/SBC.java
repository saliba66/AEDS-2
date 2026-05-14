import java.util.Scanner; 

class SBC {
    int[] entrada;
    int[] ciclo;
    int n = 0;

    SBC(int x) {
        entrada = new int[x];
        ciclo = new int[x];
    }
    void inserir(int ent, int cic) {
        if (n >= entrada.length) {
            return;
        }
        ciclo[n] = cic;
        entrada[n] = ent;
        n++;
    }
    int remover() {
        if (n == 0) {
            return -1;
        }
        int menor = 0;
        for (int i = 1; i < n; i++) {
            if (ciclo[i] < ciclo[menor]) {
                menor = i;
            }
        }
        int resp = entrada[menor];
        for (int j = menor; j < n-1; j++) {
            entrada[j] = entrada[j+1];
        }
        
        return resp;
    }

    int ciclo() {
        if (n == 0) {
            return -1;
        }
        int menor = 0;
        for (int i = 1; i < n; i++) {
            if (ciclo[i] < ciclo[menor]) {
                menor = i;
            }
        }int resp = ciclo[menor]; 
        for (int j = menor; j <n -1 ; j++) {
            ciclo[j] = ciclo[j+1];
        }
        
        return resp;
    }
        

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int x = scanf.nextInt();
        while (x != 0) {
            SBC fila = new SBC(x);
            long espera = 0;
            int contador = 0;
            for (int i = 0; i < x; i++) {
                int entrada = scanf.nextInt();
                int ciclos = scanf.nextInt();
                if (entrada == 1) {
                    contador = entrada + ciclos;
                } else {
                    fila.inserir(entrada, ciclos);
                }
            }
            while(fila.n > 0){
                espera  += contador - fila.remover();
                contador += fila.ciclo();
                fila.n--;
            }
            System.out.print(espera);
            x = scanf.nextInt();
        }
        scanf.close();
    }
}