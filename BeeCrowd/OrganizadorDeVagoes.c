#include<stdio.h>

void swap(int array[],int i, int j)
{
    int tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
}

int ordenar(int array[],int x)
{
    int cont = 0;
    for(int i = 0; i<x-1; i++)
    {
        for(int j = 0; j<x-1-i; j++)
        {
            if(array[j]>array[j+1])
            {
                swap(array,j,j+1);
                cont++;
            }
        }
    }
    return cont;
}

int main()
{
    int n;
    scanf("%d",&n);
    int i = 0;
    while(i<n)
    {
        int x;
        scanf("%d",&x);
        int array[x];
        for(int j = 0; j<x; j++)
        {
            scanf("%d",&array[j]);
        }
        int resp = ordenar(array,x);
        printf("Optimal train swapping takes %d swaps.",resp);
        i++;
    }
}
