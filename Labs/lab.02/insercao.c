
#include <stdio.h>

void swap(int x, int y, int v[])
{
    int tmp = v[y];
    v[y] = v[x];
    v[x] = tmp;
}

void insercao(int v[], int n)
{
    for (int i = 0; i < n - 1; i++)
    {
        int menor = i;
        for (int j = i + 1; j < n; j++)
        {
            if (v[j] < v[menor])
            {
                menor = j;
            }
        }
        swap(menor, i, v);
    }
    for (int i = 0; i < n; i++)
    {
        printf("%d  ", v[i]);
    }
}

int main()
{
    int n = 6;
    int v[n];
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &v[i]);
    }
    insercao(v, n);
}