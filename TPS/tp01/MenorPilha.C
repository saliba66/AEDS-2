#include<stdio.h>

typedef struct{
int array[100];
int n;
}Pilha;

Pilha push(Pilha p,int numero){
p.array[p.n] = numero;
p.n ++;
return p;
}
Pilha pop(Pilha p){
    if (p.n > 0) {
    p.n--;
}
return p;
}

int min(Pilha p){
    int menor = 100;
    if(p.n>0){
    for(int i = 0;i<p.n;i++){
        if(p.array[i]<menor){
            menor = p.array[i];
        }
    }
    return menor;
    }else{
    return menor;
    }
}

int main(){
int x;
char metodo[100];
scanf("%d",&x);
Pilha p;
p.n = 0;
for(int i = 0; i<x;i++){
scanf("%s",metodo);
if(metodo[0] == 'P' && metodo[1] == 'U'){
    int numero;
    scanf("%d",&numero);
   p = push(p,numero);
}
else if(metodo[0]== 'P'&& metodo[1] == 'O'){
   p =  pop(p);
}
else if(metodo[0]== 'M'&& metodo[1] == 'I' ){
    int menor = min(p);
    printf("%d",menor);
}
}
}

