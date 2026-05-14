import java.util.Scanner;


class ListaEncadeada {
    public void inserirComeco(int elemento) {
        Celula nova = new Celula(elemento);
        nova.prox = primeira.prox;
        primeira.prox = nova;
        if (primeira == ultimo) {
            ultima = nova;
        }
    }

    public void inserirFim(int elemento) {
        Celula nova = new Celula(elemento);
        ultimo.prox = nova;
        ultimo = ultimo.prox;
    }

    public void inserirPos(int pos, int elemento) {
        if (pos < 0 || pos > tamanho) {
            return;
        }
        if (pos == 0) {
            inserirComeco(elemento);
            return;
        } else if (pos == tamanho) {
            inserirFim(elemento);
            return;
        }
        Celula i = primeiro;
        int j = 0;
        while (j < pos) {
            i = i.prox;
            j++;
        }
        Celula nova = new Celula(elemento);
        nova.prox = i.prox;
        i.prox = nova;
    }

    public int removerInicio() {
        if (primeiro == ultimo) {
            return -1;
        } else {
            int resp = primeiro.prox.elemento;
            primeiro.prox = primeiro.prox.prox;
            return resp;
        }
    }

    public int removerFim() {
        if (primeiro == ultimo) {
            return -1;
        } else {
            int resp = ultimo.elemento;
            Celula nova = primeiro;
            while (nova != ultimo) {
                nova = nova.prox;
            }
            ultimo = nova;
            ultimo.prox = null;
            return resp;
        }
    }

    public int removerPos(int pos) {
        if (pos < 0 || pos > tamanho) {
            return -1;
        } else if (pos == 0) {
            return removerInicio();
        } else if (pos == tamanho - 1) {
            return removerFim();
        } else {
            Celula i = primeiro;
            int j = 0;
            while (j < pos) {
                i = i.prox;
                j++;
            }
            int resp = i.prox.elemento;
            i.prox = i.prox.prox;
            return resp;
        }
    }

    public int removerPos(int pos,Lista*L) {
        if (pos < 0 || pos > tamanho) {
            return -1;
        } else if (pos == 0) {
           return removerInicio();
        } else if (pos == tamanho-1) {
             return removerFim();
        } else {
            Celula *i =L-> primeiro;
            int j = 0;
            while (j < pos) {
                i = i->prox;
                j++;
            }
            int resp = i->prox->elemento;
            Celula*tmp =  i->primeiro->prox
            i->prox = tmp->prox;
            free(tmp)
            return resp;
        }
    }

    public void mostrar() {
        Celula i = primeiro.prox;
        while (i != null) {
            System.out.println(i.elemento);
            i = i.prox;
        }
    }

}

    CelulaLista maiorPilha() {
        CelulaLista i = inicio;
        CelulaLista resp = null;
        int maior = 0;
        CelulaPilha tmp = null;
        while (i != null) {
            int cont = 0;
            tmp = i.topo;
            while (tmp != null) {
                tmp = tmp.prox;
                cont++;
            }
            if (cont > maior) {
                maior = cont;
                resp = i;
            }
            i = i.prox;
        }
        return resp;
    }

    int contarPalavras(String padrao) {
        char inicial = padrao.charAt(0);
        No i = raiz;
        while (i != null) {
            if (inicial < i.letra) {
                i = i.esq;
            } else if (inicial > i.letra) {
                i = i.dir;
            } else if (inicial == i.letra) {
                break;
            }
        }
        if (i == null) {
            return 0;
        }
        No2 tmp = i.raiz;
        int cont = 0;
        int resp = varrerArvore(tmp, cont, padrao);
        return resp;
    }

    int varrerArvore(No2 tmp, int cont, String padrao) {
        if (tmp != null) {
            if (tmp.palavra.length() == padrao.length()) {
                cont++;
            }
            cont = varrerArvore(tmp.esq, cont, padrao);
            cont = varrerArvore(tmp.dir, cont, padrao);
        }
        return cont;
    }


    int [] maiorVet(int[]a, int[]b){
    int tamanho1 = a.length; 
    int tamanho2 = b.length; 
    int tam = tamanho1 + tamanho2;
    int cont = 0;
    int i = 0;
    int []vet = new int[tam];
    while(cont<tam){
        vet[cont] = a[tamanho1 - i-1];
        cont++; 
        vet[cont] = b[tamanho2 - i-1]; 
        i++;
        cont++
    }
    return vet
}