import java.util.Scanner; 

class PEstacionamento {
    int[] array;
    int n;
    
    PEstacionamento(int x) {
        array = new int[x];
        n = 0;
    }
    
    boolean inserir(int saida) {
        if (n == array.length) {
            return false;
        }
        if (n > 0 && saida > array[n - 1]) {
            return false;
        }
         array[n] = saida;
            n++;
        return true ;
    }

    void remover() {
        if (n == 0) {
            return;
        }
        n--;
    }

    int topo() {
        int tp = array[n - 1];
        return tp;
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        int k = scanf.nextInt();
        while (n != 0 && k != 0) {
            boolean verifica = true;
            PEstacionamento p = new PEstacionamento(k);
            int entrada;
            int saida;
                for (int i = 0; i < n; i++) {
                    entrada = scanf.nextInt();
                    saida = scanf.nextInt();
                    if(verifica){
                    while (p.n > 0 && p.topo() <= entrada) {
                        p.remover();
                    }
                        if (!p.inserir(saida)){
                            verifica = false;
                    }
                }
            }
            if (verifica) {
                System.out.print("SIM");
            }
            else if (!verifica) {
                System.out.println("NAO");
            }
            n = scanf.nextInt();
            k = scanf.nextInt(); 
        }
    }


}