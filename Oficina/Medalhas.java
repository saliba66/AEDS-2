import  java.util.Scanner;

class Medalhas{
    String nome;
    int ouro; 
    int prata;
    int bronze;

    public static void swap(int maior,int i, Medalhas[]paises){
        Medalhas tmp; 
        tmp = paises[maior];
        paises[maior] = paises[i];
        paises[i] = tmp;
    }

    public  static void ordenar(Medalhas[] paises) {
        for (int i = 0; i < paises.length - 1; i++) {
            int maior = i;
            for (int j = i + 1; j < paises.length; j++) {
                if (paises[maior].ouro < paises[j].ouro) {
                    maior = j;
                } else if (paises[maior].ouro == paises[j].ouro) {
                    if (paises[maior].prata < paises[j].prata) {
                        maior = j;
                    } else if (paises[maior].prata == paises[j].prata) {
                        if (paises[maior].bronze < paises[j].bronze) {
                            maior = j;
                        } else if (paises[maior].bronze == paises[j].bronze) {
                            if (paises[maior].nome.compareTo(paises[j].nome) > 0) {
                                maior = j;
                            }
                        }
                    }
                }
            }
            swap(maior, i, paises); 
        }
    }

    public static void main(String[]args){ 
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        Medalhas[] paises = new Medalhas[n];
        for (int i = 0; i < n; i++) {
            paises[i] = new Medalhas();
            paises[i].nome = scanf.next();
            paises[i].ouro = scanf.nextInt();
            paises[i].prata = scanf.nextInt();
            paises[i].bronze = scanf.nextInt();
        }
        ordenar(paises);
        for (int j = 0; j < paises.length; j++) {
            System.out.print(paises[j].nome + " ");
            System.out.print(paises[j].ouro + " ");
            System.out.print(paises[j].prata + " ");
            System.out.println(paises[j].bronze);
        }
    }

}