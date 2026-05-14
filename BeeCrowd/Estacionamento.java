import java.util.Scanner;

class Estacionamento {
    int[] vagas;
    int n;

    Estacionamento( int k) {
        vagas = new int[k];
        n = 0;
    }

    boolean Inserir(int saida) {
        if (n == vagas.length) {
            return false;
        } else if (n > 0 && saida > vagas[n - 1]) {
            return false;
        }
        vagas[n] = saida;
        n++;
        return true;
    }

    void Remover() {
        if (n == 0) {
            return;
        }
        n--;
        return;
    }

    int topo() {
        return vagas[n - 1];
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        int k = scanf.nextInt();
        while (!(n == 0 && k == 0)) {
            boolean verifica = true;
            Estacionamento pilha = new Estacionamento( k);
            for (int i = 0; i < n; i++) {
                int entrada = scanf.nextInt();
                int saida = scanf.nextInt();
                while (pilha.n > 0 && entrada >= pilha.topo()) {
                    pilha.Remover();
                }
                verifica = pilha.Inserir(saida);
                if (verifica == false) {
                    i = n;
                }
                    }
            if (verifica == true) {
                        System.out.print("Sim");
                    } else {
                        System.out.println("Nao");
                    }
                n = scanf.nextInt();
                k = scanf.nextInt();
            
        }
    }
}