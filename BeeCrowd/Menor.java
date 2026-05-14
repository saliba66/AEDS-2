import java.util.Scanner; 

class Menor{ 
    int[]array;
    int n;


    Menor(int x){
        array = new int[x];
        n = 0;
    }

    void push(int x){
        if(n==array.length){
            return;
        }
        array[n] = x;
        n++;
    }
    void pop(){
        if(n == 0){
            return;
        }
        n--;
    return;
    }

    int min(){
        int menor = array[0];
        if(n == 0){ 
            return -1;
        }
        for(int i = 0; i< n;i++){
            if(array[i]<menor){
                menor = array[i];
            }
        }
        return menor;
    }

    public static void main(String[]args){
        Scanner scanf = new Scanner(System.in);
        int c = scanf.nextInt();
        Menor pilha = new Menor(c);
        String comando = scanf.next();
        for(int i = 0 ; i <c; i ++){
        if(comando.charAt(0) == 'P' && comando.charAt(1) == 'U'){
            int x = scanf.nextInt();
            pilha.push(x); 
        }else if(comando.charAt(0) == 'P' && comando.charAt(1) == 'O'){
            pilha.pop();
        }
        else if(comando.charAt(0) == 'M' && comando.charAt(1) == 'I'){
            int resp = pilha.min();
            System.out.println(resp);
        }
        comando = scanf.next(); 
    }
    
}
}