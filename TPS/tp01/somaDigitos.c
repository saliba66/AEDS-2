#include<stdio.h>

int soma(int n){
if(n==0){
return (0);
}
else
{
return (n%10)+soma(n/10);       // soma o primeiro elemento do numero a uma variavel auxiliar, logo 1234, 1 chamada fica 4 + 123, e assim vai
}
}
int main(){
int x;
while (scanf("%d", &x)== 1){        // condicao de parada do loop e a digitaçao de algo que nao seja um inteiro
printf("%d \n",soma(x));
}
return 0;
}
