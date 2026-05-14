import java.util.Scanner; 

class Sort {
    public static void swap(int[] N, int menor, int i) {
        int tmp = N[i];
        N[i] = N[menor];
        N[menor] = tmp;
    }

    public static void ordenar(int[] N, int M) {
        for (int i = 0; i < N.length - 1; i++) {
            int menor = i;
            for (int j = 1 + i; j < N.length; j++) {
                if (N[j] % M < N[menor] % M) {
                    menor = j;
                } else if (N[j] % M == N[menor] % M) {
                    if (N[j] % 2 == 1 && N[menor] % 2 == 0) {
                        menor = j;
                    } else if (N[j] % 2 == 1 && N[menor] % 2 == 1) {
                        if (N[j] > N[menor]) {
                            menor = j;
                        }
                    } else if (N[j] % 2 == 0 && N[menor] % 2 == 0) {
                        if (N[j] < N[menor]) {
                            menor = j;
                        }
                    }
                }
            }
            swap(N, menor, i);
        }

        for (int k = 0; k < N.length; k++) {
                System.out.println(N[k]); 
            }
    }
        public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int numeros = scanf.nextInt();
        int M = scanf.nextInt();
        while (numeros != 0 && M != 0) {
            int[] N = new int[numeros];
            for (int i = 0; i < numeros; i++) {
                N[i] = scanf.nextInt();
            }
            ordenar(N, M);
            numeros = scanf.nextInt();
            M = scanf.nextInt();
        }
    }
}