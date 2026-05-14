LISTA DUPLAMENTE ENCADEADA

inseirirInicio(int x, ListaDupla*L){
    Celula * nova = malloc(sizeof(Celula));
    nova->elemento = x;
    nova -> prox = L->primeira->prox;
    nova -> ant = L->primeira
    L->primeira->prox = nova
    if(L->primeira == L->ultimo){
        ultimo = nova;
    }else {
        nova->prox->ant = nova
    }
}
InserirFim(int x, ListaDupla*L){
    Celula*nova = malloc(sizeof(Celula));
    nova -> elemento = x;
    L->ultimo->prox = nova;
    nova->ant = L->ultimo;
    L->ultimo = nova; 
    nova -> prox = null;
    
}
InserirPos(int x, int pos, ListaDupla*L){
    Celula*nova = malloc(sizeof(Celula));
    nova ->elemento = x;
    Celula*i = L->primeiro;
    int cont = 0; 
    while(cont<pos){
        i = i->prox;
        cont++;
    }
    nova->prox = i->prox;
    nova -> ant = i;
    i ->prox = nova;
    if(L->primeiro == L->ultimo){
       L-> ultimo = nova;
    }else{
        nova->prox->ant = nova; 
    }
}
int RemoverInicio()