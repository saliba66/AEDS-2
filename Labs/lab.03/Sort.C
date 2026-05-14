#include <stdio.h>

void swap(int N[], int maior, int j){
    int tmp = N[maior];
    N[maior] = N[j];
    N[j] = tmp;
}

 void ordenar(int N[], int qtd, int M)
{
    for (int i = 0; i < qtd - 1; i++)
    {
        int maior = i;
        for (int j = i + 1; j<qtd; j++)
        {
            if(N[j] % M < N[maior]% M)
            {
                maior = j;
            }
            else if(N[maior]% M == N[j]% M)
            {
                if (N[maior] % 2 == 0 && N[j] % 2 != 0)
                {
                    maior = j;
                }
                else if (N[maior] % 2 != 0 && N[j] % 2 != 0 )
                {
                    if (N[maior] < N[j] )
                    {
                        maior = j;
                    }
                }
                else if (N[maior] % 2 == 0 && N[j] % 2 == 0 )
                {
                    if (N[maior] > N[j])
                    {
                        maior = j;
                    }
                }
            }
        }
        swap(N, maior,i);
    }
    for (int k = 0 ;  k < qtd; k++)
    {
        printf("%d \n", N[k]);
    }
}

int  main()
{
    int qtd;
    scanf("%d", &qtd);
    int M;
    scanf("%d", &M);
    while (M != 0 && qtd != 0)
    {
        int N[qtd];
        for (int i = 0; i < qtd; i++)
        {
            scanf("%d", &N[i]);
        }
        ordenar(N, qtd, M);
        scanf("%d", &N);
        scanf("%d", &qtd);
    }
}
