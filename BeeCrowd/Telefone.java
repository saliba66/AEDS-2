import java.util.Scanner;

class Telefone {

    public static void swap(String[] telefones, int i, int j) {
        String tmp = telefones[i];
        telefones[i] = telefones[j];
        telefones[j] = tmp;
    }

    public static void ordenar(String[] telefones) {
        for (int i = 0; i < telefones.length-1; i++) {
            for (int j = 0; j < telefones.length - 1 - i; j++) {
                if (telefones[j].compareTo(telefones[j + 1])>0) {
                    swap(telefones, j, j + 1);
                }
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        while (scanf.hasNext()) {
            int n = scanf.nextInt();
            scanf.nextLine();
            String[] telefones = new String[n];
            for (int i = 0; i < n; i++) {
                telefones[i] = scanf.nextLine();
            }
            ordenar(telefones);
            int cont = 0;
            int j = 0;
            while (j < telefones.length-1) {
                for (int i = 0; i < telefones[j].length(); i++) {
                    if (telefones[j].charAt(i) == telefones[j + 1].charAt(i)) {
                        cont++;
                    } else if (telefones[j].charAt(i) != telefones[j + 1].charAt(i)) {
                        break;
                    }
                }
                j++;
            }
            System.out.println(cont);
        }
    }
}