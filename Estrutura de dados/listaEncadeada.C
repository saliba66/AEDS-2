#include<stdio.h>

typedef struct Celula{
int elemento;
struct Celula* prox;
}Celula;

Celula* newCelula(int x){
Celula* nova = Celula*(malloc)(sizeof(Celula));
nova->elemento = x;
nova ->prox = NULL;
return nova;
}

Celula *primeiro;
Celula *ultimo;

void comeco(){
primeiro = newCelula(-1);
ultimo = primeiro;
}

void inserirInicio(int x){
Celula*nova = newCelula(x);
nova->prox = primeiro->prox;
primeiro->prox =nova;
}


void inserirFim(int x){
ultimo->prox = newCelula(x);
ultimo = ultimo->prox;
}

int removerInicio(){
if(ultimo==primeiro){
return -1;
}
Celula*tmp = primeiro->prox;
int resp = tmp->elemento;
primeiro->prox = tmp->prox;
 if(tmp == ultimo){
ultimo = primeiro;
 }
free(tmp);
return resp;
}

int removerFim(){
    if(primeiro == ultimo){
            return -1;
    }
    Celula*i = primeiro;
    int resp = ultimo->elemento;
    while(i->prox!=ultimo){
    i = i->prox;
    }
    Celula*tmp = ultimo;
    ultimo = i;
    ultimo->prox = NULL;
    free(tmp);
    return resp;
}

int removerPos(int pos){

}



void mostrar(){
Celula*i = primeiro->prox;
while(i!= NULL){
    printf(i->elemento);
    i = i->prox;
}
}

int main(){




}

void inserirFim(int x){
    CelulaDupla* nova = novaCelulaDupla(x); 
    nova->ant = ultima;
    nova->prox = primeira;
    ultima->prox = nova;
    ultima = nova
    primerio ->ant = nova
}