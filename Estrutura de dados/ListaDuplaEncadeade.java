import java.util.Scanner; 

public void inserirInicio(int x) {
    CelulaDupla nova = new CelulaDupla(x);
    nova.ant = primeiro;
    nova.prox = primeria.prox;
    primeiro.prox = nova;
    if (primeiro == ultimo) {
        ultimo = nova;
    } else {
        nova.prox.ant = nova;
    }
    public void InserirFim(int x){
        CelulaDupla nova = new CelulaDupla(x);
        nova.ant = ultimo;
        ultimo.prox = nova;
        ultimo = ultimo.prox;
    }
    public void InserirPos(int x, int pos){
        if(pos<0 || pos>tamanho){
            return;
        } else if( pos == 0){
            inserirInicio(x);
            return;
        } else if( pos == tamanho){
            InserirFim(x);
            return;
        }else {
            Celula i = primeiro;
            int j = 0;
            while(j<pos){
                i = i.prox;
                j++;
            }
            CelulaDupla nova = new CelulaDupla(x); 
            nova.ant = i;
            nova.prox = i.prox;
            i.prox = nova;
            nova.prox.ant = nova;
        }
    }
    public int RemoverInicio(){
        if(primeiro == ultimo){
            return -1;
        }else{
            int resp = primeiro.prox.elemento;
            primeiro.prox = primeiro.prox.prox;
            primeiro.prox.ant = primeiro;
            return resp;
        }
    }
    public int RemoverFim(){
        if(primeiro == ultimo){
            return -1;
        }else{
            int resp = ultimo.elemento;
            ultimo = ultimo.ant;
            ultimo.prox = null;
        }
    }
    public int RemoverPos(int pos){
        if(pos<0 || pos>tamanho){
            return -1;
        }
        else if(pos == 0){
            removerInicio();
            return;
        } else if(pos == tamanho-1){
            removerFim();
            return;
        }else{
            Celula i = primeiro;
            int j = 0; 
            while(j<pos){
                i= i.prox;
                j++;
            }
            int resp = i.prox;
            i.prox.= i.prox.prox;
            i.prox.ant = i;
            return resp;
        }
        }
        }
    

}


public Celula maiorPilha() {
    if (inicio == null) return null;
    Celula lista = inicio;
    Celula maior = inicio;
Celula pilha = inicio.topo
    int maiorTam = 0;

    while (lista != null) {
        int cont = 0;
        while (pilha != null) {
            cont++;
            pilha = pilha.prox;
        }

        // compara
        if (cont > maiorTam) {
            maiorTam = cont;
            maior = lista;
        }

        lista = lista.prox;
    }

    return maior;
}