#include<stdio.h>

int main(){
char falhado;
char numero[100];
scanf(" %c %s", &falhado, numero);
while(!(falhado =='0' && numero[0] == '0' && numero[1] == '\0')){
    char novo [100];
    int k = 0;
    for(int i = 0 ; numero[i]!= '\0'; i++){
        if(numero[i]!= falhado){
            novo[k] = numero[i];
            k++;
        }
    }
    novo[k] = '\0';
    int n = 0;
    while(n<k && novo[n] == '0'){
        n++;
    }
    if(k == n){
        printf("0");
    }
    else{
    printf("%s", novo + n);
    }
    scanf(" %c %s", &falhado, numero);
}
return 0 ;

}
