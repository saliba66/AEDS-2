#include<stdio.h>
#include<string.h>
struct Camisetas
{
    char cor[100];
    char tamanho;
    char nome[100];
} typedef Camisetas;

void swap(Camisetas alunos[], int menor,int i)
{
    Camisetas tmp = alunos[i];
    alunos[i] = alunos[menor];
    alunos[menor] = tmp;
}


void ordenar(Camisetas alunos[],int n)
{
    for(int i = 0; i < n-1; i++)
    {
        int menor = i;
        for(int j = i+1; j< n; j++)
        {
            if(alunos[menor].cor[1] == 'E'&& alunos[j].cor[1] == 'r')
            {
                menor = j;
            }
            else if(strcmp(alunos[menor].cor,alunos[j].cor) == 0)
            {
                if ((alunos[j].tamanho == 'G' && alunos[menor].tamanho != 'G') ||(alunos[j].tamanho == 'M' && alunos[menor].tamanho == 'P'))
                {
                    menor = j;
                }
                else if(alunos[menor].tamanho == alunos[j].tamanho)
                {
                    int k = 0;

                    while(alunos[menor].nome[k] != '\0' && alunos[j].nome[k] != '\0')
                    {
                        if(alunos[j].nome[k] < alunos[menor].nome[k])
                        {
                            menor = j;
                            break;
                        }
                        else if(alunos[j].nome[k] > alunos[menor].nome[k])
                        {
                            break;
                        }
                        k++;
                    }

                    if(alunos[j].nome[k] == '\0' && alunos[menor].nome[k] != '\0')
                    {
                        menor = j;
                    }
                }
            }
        }
    }
    swap(alunos,menor,i);

for(int i = 0; i < n ; i ++)
{
    printf("%s %c %s\n", alunos[i].cor, alunos[i].tamanho, alunos[i].nome);
}
}



int main()
{
    int n;
    scanf("%d", &n);
    while(n>0)
    {
        Camisetas alunos[n];
        for(int i = 0; i < n; i ++)
        {
            scanf(" %[^\n]", alunos[i].nome);
            scanf("%s %c", alunos[i].cor, &alunos[i].tamanho);
        }
        ordenar(alunos,n);
        scanf("%d",&n);
    }
}


