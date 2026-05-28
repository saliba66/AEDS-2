import java.util.Scanner;

class Diamantes{
    public static int procura(String areia){
        int j = 0;
        int cont = 0;
        int cont2 = 0;
        while(j<areia.length()){
            if(areia.charAt(j) == '<'){
                cont++;
            }else if(areia.charAt(j) == '>'){
                cont2++;
            }
            j++;
        }
        int resp = 0;
        while(cont > 0 && cont2>0){
            resp++;
            cont --;
            cont2 --;
        }
        return resp;
    }

    public static void main(String[]args){
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt();
        int i = 0;
        scanf.nextLine();
        while(i<n){
            String areia = scanf.nextLine();
            int resp = procura(areia);
            System.out.println(resp);
            i++;
        }
    }
}