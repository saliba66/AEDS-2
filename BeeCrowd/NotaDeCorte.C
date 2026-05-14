#include<stdio.h>



void swap(int notas[], int menor,int i){
int tmp = notas[i];
notas[i] = notas[menor];
notas[menor] = tmp;
}

void ordenar(int notas[],int n){
for(int i = 0; i< n -1;i++){
    int menor = i;
    for(int j = i+1;j< n ; j++){
       if(notas[j]<notas[menor]){
        menor = j;
        }
        }
        swap(notas,menor,i);
    }
}

int minima(int notas[], int n ,int k){
    int minima = notas[n-k];
    return minima;
    }

int main(){
int n;
int k;
scanf("%d %d",&n,&k);
int notas[n];
for(int i = 0; i < n;i++){
scanf("%d",&notas[i]);
}
ordenar(notas,n);
int resposta = minima(notas,n,k);
printf("%d",resposta);
}
