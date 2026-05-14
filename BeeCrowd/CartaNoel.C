#include<stdio.h>

void trocar(char palavra[]){
char nova[100];
int i;
for( i = 0; palavra[i] != '\0'; i++){
if(palavra[i] == '@'){
nova[i] = 'a';}

else if(palavra[i] == '&'){
nova[i] = 'e';
}
else if(palavra[i] == '!'){
nova[i] = 'i';
}
else if(palavra[i] == '*'){
nova[i] = 'o';
}
else if(palavra[i] == '#'){
nova[i] = 'u';
}
else{
nova[i] = palavra[i];
}
}
nova[i] = '\0';
printf("% s \n", nova);
}

int main(){
char palavra[1000];
while(scanf("%s",palavra) != EOF)
{
trocar(palavra);
}
}
