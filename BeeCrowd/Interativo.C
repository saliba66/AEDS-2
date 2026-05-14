#include<stdio.h>
#include<stdbool.h>

bool vogal(char palavra[],int tamanho){
if(tamanho < 0){
return true;
}
if (palavra[tamanho] != 'A' && palavra[tamanho] != 'a' &&
    palavra[tamanho] != 'E' && palavra[tamanho] != 'e' &&
    palavra[tamanho] != 'I' && palavra[tamanho] != 'i' &&
    palavra[tamanho] != 'O' && palavra[tamanho] != 'o' &&
    palavra[tamanho] != 'U' && palavra[tamanho] != 'u') {
    return false;
}
return vogal(palavra, tamanho -1);                  //ve a string de tras para frente, identifica se o caracter é diferente de vogal, se for retorna false
}

bool consoantes(char palavra[], int tamanho){
if(tamanho<0){
return true;
}
    if (!((palavra[tamanho] >= 'A' && palavra[tamanho] <= 'Z') ||
          (palavra[tamanho] >= 'a' && palavra[tamanho] <= 'z'))) {
        return false;
    }

if(palavra[tamanho] == 'A' || palavra[tamanho] == 'a' ||
    palavra[tamanho] == 'E' || palavra[tamanho] == 'e' ||
    palavra[tamanho] == 'I' || palavra[tamanho] == 'i' ||
    palavra[tamanho] == 'O' || palavra[tamanho] == 'o' ||
    palavra[tamanho] == 'U' || palavra[tamanho] == 'u' ){
    return false;
    }
    return consoantes(palavra, tamanho -1);                          //ve a string de tras para frente, identifica se o caracter é uma vogal, se for retorna false
    }
bool inteiros(char palavra[], int tamanho){
if(tamanho<0){
return true;
}
if(!(palavra[tamanho] >= '0' && palavra[tamanho]<='9')){
return false;
}
return inteiros(palavra,tamanho -1);
}

bool reais(char palavra[], int tamanho, int pontos){
    if (tamanho < 0) {
        return true;
    }

    if (palavra[tamanho] == '.' || palavra[tamanho] == ',') {               //verifica a quantidade de pontos e virgulas na string, se tiver mais de 1 ponto ou mais de 1 virgula o número nao é real e retorna false
        pontos++;
        if (pontos > 1) {
            return false;
        }
    }
    else if (!(palavra[tamanho] >= '0' && palavra[tamanho] <= '9')) {
        return false;
    }
    return reais(palavra, tamanho - 1, pontos);
}


int main(){
char palavra[100];
scanf("%s", palavra);
int i = 0;
int pontos = 0;
while(!(palavra[0] == 'F' && palavra[1] == 'I' && palavra[2] == 'M')){
while(palavra[i] != '\0'){
i++;
}
 bool X1 = vogal(palavra,i-1);
 bool X2 = consoantes(palavra,i-1);
 bool X3 = inteiros(palavra,i-1);
 bool X4 = reais(palavra,i-1,pontos);
 if(X1){
 printf("SIM ");
 }
 else{
 printf("NAO ");   //chama todas as funcoes e vai imprimindo sim ou nao para elas
 }
 if(X2){
 printf("SIM ");
 }
 else{
 printf("NAO ");
 }
 if(X3){
 printf("SIM ");
 }
 else{
 printf("NAO ");
 }
 if(X4){
 printf(" SIM ");
 }
 else{
 printf("NAO ");
 }
 scanf("%s", palavra);
}
}
