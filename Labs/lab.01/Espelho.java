import java.util.Scanner; 

class Espelho {
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
       
        while (scanf.hasNext()) {
        int x = scanf.nextInt();
        int y = scanf.nextInt();
            String metade = "";
            String metade2 = "";
            for (int i = x; i <= y; i++) {
                metade += i;
            }
            for (int i = metade.length() - 1; i >= 0; i--) {
                metade2 += metade.charAt(i);
            }
            System.out.print(metade + metade2);
        }
        scanf.close(); 
    }

    }
