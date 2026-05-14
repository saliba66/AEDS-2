
void removerPalavraArvore(String palavra){
    char inicial = palavra.charAt(0);
    No i = raiz;
    while(i!=null){
        if(i.letra == inicial){
            break;
        }else if(i.letra>inicial){
            i = i.esq;
        }else{
            i = i.dir;
        }
    }
    if(i==null){
        return;
    }
    Celula tmp = i.primeiro;
    Celula ant = null;
    while(tmp!=null){
        if(tmp.palavra.equals(palavra)){
            if(ant == null){
                i.primeiro = tmp.prox;
            }else{
            ant.prox = tmp.prox; 
        }
        return;
        }
          ant = tmp;
            tmp = tmp.prox;
    }
    return;

void inserirFim(int x){
    CelulaDupla*nova = novaCelulaDupla(x);
    CelulaDupla*ultima = primeira->ant;
    ultima->prox = nova;
    nova->prox = primeira;
    nova->ant = ultima;
    primeira->ant = nova;
}


void removerImpares(){
    CelulaMat linha = inicio;
    while(linha!=null){
        CelulaMat coluna = linha;
        while(coluna!=null){
            Celula i = coluna.primeiro.prox;
            Celula ant = coluna.primeiro;
            while(i!=null){
                if(i.elemento%2!=0){
                    ant.prox = i.prox;
                    if(i == coluna.ultimo){
                        coluna.ultimo = ant;
                    }
                    i = ant.prox;
                }else{
                ant = i;
                i = i.prox;
            }
            coluna = coluna.prox;
        }
        linha = linha.inf;
    }
}
}


int caracteresIguais(char a, char b){
    No i = raiz;
    while(i!= null){
        if(a == i.letra){
            break;
        }else if(a<i.letra){
            i = i.esq;
        }else{
            i = i.dir;
        }
    }
    if(i== null){
        return 0;
    }
    No2 tmp = i.raiz;
    int contador = 0;
    int resp = varrer(a,b,tmp,contador){
   return resp;
}
}
int varrer(char a,char b, No2 tmp){
    int contador = 0;
    if(tmp == null){
        return 0;
    }
    
    else if(tmp.palavra.charAt(0)== a && tmp.palavra.charAt(tmp.lenght()-1 == b)){
        contador++;
    }
    contador+=(varrer a,b, tmp.esq);
    contador+=(varrer a,b, tmp.dir);
    return contador;
}




CelulaDupla intercalarReverso(CelulaDupla c1, CelulaDupla c2){
    CelulaDupla a = c1;
    CelulaDupla b = c2; 
    while(a.prox!=null){
        a = a.prox;
    }
     while(b.prox!=null){
        b = b.prox;
    }
    CelulaDupla resp = new CelulaDupla(); 
    CelulaDupla fim = resp;
    while(a!=c1 || b!=c2){
        if(a!=c1){
            CelulaDupla nova = new Celula(a.elemento);
            fim.prox = nova;
            nova.ant = fim;
             fim = nova;
            a = a.ant;
        }
         if(b!=c2){
            CelulaDupla nova = new Celula(b.elemento);
            fim.prox = nova;
            nova.ant = fim;
            fim = nova;
            b = b.ant;
        }
    }
    return resp;
}


void inserir(String palavra){
    char inicial = palavra.charAt(0);
    No i = raiz;
    while(i!=null){
        if(i.letra == inicial){
            break;
        }else if(i.letra>inicial){
            i = i.esq;
        }else{
            i = i.dir;
        }
    }
    Celula tmp = i.primeiro;
    Celula ant = null;
    Celula nova = new Celula(palavra);
    while(tmp!= null && tmp.palavra.compareTo(palavra)<0){
        ant = tmp;
        tmp = tmp.prox;
    }
 if(ant == null){
        nova.prox = i.primeiro;
        i.primeiro = nova;
    } else {
        nova.prox = tmp;
        ant.prox = nova;
    }

    if(tmp == null){
        i.ultimo = nova;
    }
}
    }
