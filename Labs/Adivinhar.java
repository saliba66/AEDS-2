import java.util.Scanner;

class Adivinhar {

    public static int inserirP(int[] arrayP, int numero, int np) {
        arrayP[np] = numero;
        return np + 1;
    }
    public static int inserirL(int[] arrayL, int numero, int nl) {
        arrayL[nl] = numero;
        return nl + 1;
    }
    public static int inserirLP(int[] arrayLP, int numero, int nlp) {
        arrayLP[nlp] = numero;
        return nlp + 1;
    }

    public static int removerP(int[] arrayP, int np) {
        return arrayP[np - 1];
    }
    public static int removerL(int[] arrayL, int nl) {
        int removido = arrayL[0];
        for (int i = 0; i < nl - 1; i++) {
            arrayL[i] = arrayL[i + 1];
        }
        return removido;
    }
    public static int removerLP(int[] arrayLP, int nlp) {
        int maior = 0;
        for (int i = 1; i < nlp; i++) {
            if (arrayLP[i] > arrayLP[maior]) {
                maior = i;
            }
        }
        int removido = arrayLP[maior];
        for (int i = maior; i < nlp - 1; i++) {
            arrayLP[i] = arrayLP[i + 1];
        }
        return removido;
    }

    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);

        while (scanf.hasNext()) {
            int n = scanf.nextInt();

            int np = 0;
            int nl = 0;
            int nlp = 0;

            int[] arrayP = new int[n];
            int[] arrayL = new int[n];
            int[] arrayLP = new int[n];

            boolean pilha = true;
            boolean fila = true;
            boolean prioridade = true;

            for (int i = 0; i < n; i++) {
                int comando1 = scanf.nextInt();
                int comando2 = scanf.nextInt();

                if (comando1 == 1) {
                    int numero = comando2;

                    np = inserirP(arrayP, numero, np);
                    nl = inserirL(arrayL, numero, nl);
                    nlp = inserirLP(arrayLP, numero, nlp);

                } else if (comando1 == 2) {
                    int verificar = comando2;

                    if (np == 0) {
                        pilha = false;
                        fila = false;
                        prioridade = false;
                    } else {
                        int rp = removerP(arrayP, np);
                        int rl = removerL(arrayL, nl);
                        int rlp = removerLP(arrayLP, nlp);

                        if (verificar != rp) {
                            pilha = false;
                        }
                        if (verificar != rl) {
                            fila = false;
                        }
                        if (verificar != rlp) {
                            prioridade = false;
                        }

                        np--;
                        nl--;
                        nlp--;
                    }
                }
            }

            int total = 0;

            if (pilha) {
                total++;
            }
            if (fila) {
                total++;
            }
            if (prioridade) {
                total++;
            }

            if (total == 0) {
                System.out.println("impossible");
            } else if (total > 1) {
                System.out.println("not sure");
            } else if (pilha) {
                System.out.println("stack");
            } else if (fila) {
                System.out.println("queue");
            } else {
                System.out.println("priority queue");
            }
        }

        scanf.close();
    }
}