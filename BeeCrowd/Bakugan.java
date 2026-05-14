import java.util.Scanner; 

class Bakugan {

    public static int pontos(int[] j) {
        int total = 0;
        for (int i = 0; i < j.length; i++) {
            total += j[i];
        }
        return total;
    }
    public static int bonus(int[] j) {
        for (int i = 0; i < j.length - 2; i++) {
            if (j[i] == j[i + 1] && j[i + 1] == j[i + 2]) {
                return i;
            }
        }
        return -1; 
    }
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int r = scanf.nextInt();
        while (r != 0) {
            int[] Mark = new int[r];
            int[] Leti = new int[r];
            for (int i = 0; i < r; i++) {
                Mark[i] = scanf.nextInt();
            }
            for (int i = 0; i < r; i++) {
                Leti[i] = scanf.nextInt();
            }
            int M = pontos(Mark);
            int L = pontos(Leti);
            int Mbonus = bonus(Mark);
            int Lbonus = bonus(Leti);
            if (Mbonus < Lbonus) {
                M = M + 30;
            } else if (Lbonus < Mbonus) {
                L = L + 30;
            }
            if (L > M) {
                System.out.print("L");
            } else if (M > L) {
                System.out.print("M");
            }
            if (M == L) {
                System.out.print("T");
            }
            r = scanf.nextInt(); 
        }
            
        }
    }