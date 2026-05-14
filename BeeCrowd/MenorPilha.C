#include<stdio.h>

typedef struct{
int array[100];
int n;
}Pilha;

Pilha push(int x, Pilha p){
if(p.n<100){
p.array[p.n] = x;
p.n++;
}
return p;
}

Pilha pop(Pilha p){
if(p.n == 0){
    return p;
}
int resp = p.array[p.n-1];
p.n--;
printf("%d", resp);
return p;
}

void Min(Pilha p){
if(p.n == 0){
return;
}
int menor = p.array[0];
for(int i = 0; i<p.n;i++){
if(p.array[i] < menor){
menor = p.array[i];
}
}
printf("%d",menor);
}




int main(){
Pilha p;
p.n = 0;
char comando[100];
scanf("%s",comando);
while(!(comando[0] == 'F' && comando[1] == 'I')){
if(comando[0] == 'P' && comando[1] == 'U'){
int x;
scanf("%d",&x);
p = push(x,p);
}
if(comando[0] == 'M'){
Min(p);
}
if(comando[0] == 'P' && comando[1] == 'O'){
    p = pop(p);
}
scanf("%s",comando);
}
}

