#include<stdio.h>

int verificar(int array[],int alunos,int sorteado){
int proximo = 100;
int indice = 0;
for(int i = 0; i <alunos ; i++){
if(array[i] == sorteado){
    return i+1;
    }
else{
    int teste = array[i] - sorteado;
    if(teste<0){
    teste = teste* -1;
    }
    if(teste<proximo){
    indice = i;
    }
}
}
return indice+1 ;
}


int main(){
int n;
int alunos;
int sorteado;
scanf("%d", &n);
for(int i = 0; i< n ; i++){
scanf("%d",&alunos);
int array[alunos];
scanf("%d",&sorteado);
for(int j = 0; j<alunos;j++){
scanf("%d",&array[i]);
}
int resp = (verificar(array,alunos, sorteado));
printf("%d",resp);
}




}
