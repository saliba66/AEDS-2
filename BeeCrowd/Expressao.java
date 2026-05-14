import java.util.Scanner; 

class Expressao {
    
public static boolean verifica(String frases){
    for(int i = 0; i <frases.length();i++){
        if(!(frases.charAt(i) == frases.charAt(frases.length()-i))){
            return false;  
        } else if (!(frases.charAt(i) == frases.charAt(i+1))){
            return false ; 
        }
    }
    return true ; 
}   public static void main(String []args){
        Scanner scanf = new Scanner(System.in);
        int n = scanf.nextInt(); 
        scanf.nextLine(); 
        String frases = scanf.nextLine();  
        for(int i = 0; i <n; i++){
          boolean resp =  verifica(frases);
            if(resp == true){
                System.out.println("S");
            }else{
                System.out.println("N");
            }
            frases = scanf.nextLine(); 
        }
    }
}