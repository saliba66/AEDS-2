#include <stdio.h>
#include <string.h>

struct Pais
{
    char nome[100];
    int ouro;
    int prata;
    int bronze;
} typedef Pais;

void swap(int maior, int i, Pais p[])
{
    Pais tmp = p[i];
    p[i] = p[maior];
    p[maior] = tmp;
}

void ordenar(int n, Pais p[])
{
    for (int i = 0; i < n - 1; i++)
    {
        int maior = i;
        for (int j = i + 1; j < n; j++)
        {
            if (p[maior].ouro < p[j].ouro)
            {
                maior = j;
            }
            else if (p[maior].ouro == p[j].ouro)
            {
                if (p[maior].prata < p[j].prata)
                {
                    maior = j;
                }
            }
            else if (p[maior].prata == p[j].prata)
            {
                if (p[maior].bronze < p[j].bronze)
                {
                    maior = j;
                }
            }
            else if (p[maior].bronze == p[j].bronze)
            {
                if (strcmp(p[j].nome, p[maior].nome) < 0)
                {
                    maior = j;
                }
            }
        }
        swap(maior, i, p);
    }
}
int main()
{
    int n;
    scanf("%d", &n);
    Pais p[n];
    for (int i = 0; i < n; i++)
    {
        scanf("%s", p[i].nome);
        scanf("%d", &p[i].ouro);
        scanf("%d", &p[i].prata);
        scanf("%d", &p[i].bronze);
    }
    ordenar(n, p);
    for (int i = 0; i < n; i++)
    {
        printf("%s %d %d %d\n", p[i].nome, p[i].ouro, p[i].prata, p[i].bronze);
    }

    return 0;
}
