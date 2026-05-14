#include <stdio.h>
#include <stdlib.h>

typedef struct Celula
{
    int elemento;
    struct Celula *prox;
} Celula;

Celula *newCelula(int x)
{
    Celula *nova = Celula * (malloc)(sizeof(Celula));
    nova->elemento = x;
    nova->prox = NULL;
    return nova;
}
typedef struct Pilha
{
    Celula *topo;
} Pilha

    void
    inserir(int x, Pilha *pilha)
{
    Celula *nova = Celula * (malloc)(sizeof(Celula));
    nova->elemento = x; // cria a nova celula
    nova->prox = topo;  // a setinha � como se fosse o . em java logo nova->prox � = a nova.prox
    topo = nova;
}

int remover(Pilha *pilha)
{
    if (pilha->topo == NULL)
    {
        return -1;
    }
    Celula *tmp = pilha->topo;
    int resp = tmp->elemento;
    pilha->topo = tmp->prox;
    free(tmp);
    return resp;
}

void mostrar(Pilha *pilha)
{
    Celula *tmp = pilha->topo;
    while (tmp != NULL)
    {
        printf("%dtmp->elemento");
        tmp = tmp->prox;
    }
}

Pilha *criarNova(Pilha *pilha)
{
    Pilha *nova = Pilha * (malloc)sizeof(Pilha);
    int removido;
    while (pilha->topo != NULL)
    {
        removido = remover(pilha);
        if (removido % 2 != 0)
        {
            inserir(removido, nova);
        }
    }
    return nova;
}

int main()
{
}
