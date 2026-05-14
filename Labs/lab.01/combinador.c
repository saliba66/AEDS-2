#include <stdio.h>
#include <string.h>
void combinador(char palavra1[], char palavra2[])
{
    int x = strlen(palavra1);
    int y = strlen(palavra2);
    int maior = 0;
    int k = 0;
    char nova[100];
    if (x < y)
    {
        maior = y;
    }
    else
    {
        maior = x;
    }
    for (int i = 0; i < maior; i++)
    {
        if (i < x)
        {
            nova[k++] = palavra1[i];
        }
        if (i < y)
        {
            nova[k++] = palavra2[i];
        }
    }
    nova[k] = '\0';
    printf("%s", nova);
}

int main()
{
    char palavra1[100];
    char palavra2[100];
    while(scanf("%s %s",palavra1,palavra2)!='EOF'){
    combinador(palavra1, palavra2);
    }
    return 0;
}
