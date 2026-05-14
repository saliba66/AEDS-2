#include <stdio.h>
#include <string.h>
#include <time.h>

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int hora;
    int minuto;
} Hora;

typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tipos_cozinha[10][50];
    int n_tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura;
    Hora horario_fechamento;
    Data data_abertura;
    int aberto;
} Restaurante;

typedef struct {
    int tamanho;
    Restaurante restaurantes[10000];
} ColecaoRestaurantes;

// -------------------------- PARSE DATA --------------------------

Data parse_data(char *s) {
    Data d;
    sscanf(s,"%d-%d-%d",&d.ano,&d.mes,&d.dia);
    return d;
}

// -------------------------- PARSE HORA --------------------------

Hora parse_hora(char *s){
    Hora h;
    sscanf(s,"%d:%d",&h.hora,&h.minuto);
    return h;
}

// -------------------------- PARSE RESTAURANTE --------------------------

Restaurante parse_restaurante(char *s){
    Restaurante r;

    char tipos[200],preco[10],horario[20],data[20],aberto[10];
    char h1[10],h2[10];

    sscanf(s,"%d,%99[^,],%99[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9[^\n\r]",
        &r.id,r.nome,r.cidade,&r.capacidade,&r.avaliacao,
        tipos,preco,horario,data,aberto);

    // tipos cozinha
    int i=0,j=0,k=0;
    while(tipos[i] != '\0'){
        if(tipos[i]==';'){
            r.tipos_cozinha[k][j]='\0';
            k++;
            j=0;
        } else {
            r.tipos_cozinha[k][j]=tipos[i];
            j++;
        }
        i++;
    }
    r.tipos_cozinha[k][j]='\0';
    r.n_tipos_cozinha = k+1;

    // faixa preco
    r.faixa_preco = 0;
    while(preco[r.faixa_preco] != '\0'){
        r.faixa_preco++;
    }

    // horario
    sscanf(horario,"%[^-]-%s",h1,h2);
    r.horario_abertura = parse_hora(h1);
    r.horario_fechamento = parse_hora(h2);

    // data
    r.data_abertura = parse_data(data);

    // aberto
    r.aberto = strcmp(aberto,"true") == 0;

    return r;
}

// -------------------------- FORMATAR --------------------------

void formatar_restaurante(Restaurante *r,char *buffer){
    char data[20],h1[10],h2[10];

    sprintf(data,"%02d/%02d/%04d",r->data_abertura.dia,r->data_abertura.mes,r->data_abertura.ano);
    sprintf(h1,"%02d:%02d",r->horario_abertura.hora,r->horario_abertura.minuto);
    sprintf(h2,"%02d:%02d",r->horario_fechamento.hora,r->horario_fechamento.minuto);

    int pos=0;

    pos+=sprintf(buffer+pos,"[%d ## %s ## %s ## %d ## %.1lf ## [",
        r->id,r->nome,r->cidade,r->capacidade,r->avaliacao);

    for(int i=0;i<r->n_tipos_cozinha;i++){
        pos+=sprintf(buffer+pos,"%s",r->tipos_cozinha[i]);
        if(i<r->n_tipos_cozinha-1) pos+=sprintf(buffer+pos,",");
    }

    pos+=sprintf(buffer+pos,"] ## ");

    for(int i=0;i<r->faixa_preco;i++) pos+=sprintf(buffer+pos,"$");

    pos+=sprintf(buffer+pos," ## %s-%s ## %s ## %s]",
        h1,h2,data,r->aberto?"true":"false");
}

// -------------------------- CSV --------------------------

void remover_quebra_linha(char *linha){
    int i=0;
    while(linha[i] != '\0'){
        if(linha[i] == '\n' || linha[i] == '\r'){
            linha[i] = '\0';
        }
        i++;
    }
}

void ler_csv(ColecaoRestaurantes *c){
    FILE *f = fopen("/tmp/restaurantes.csv","r");
    char linha[512];
    c->tamanho=0;

    if(f==NULL) return;

    fgets(linha,sizeof(linha),f);

    while(fgets(linha,sizeof(linha),f)){
        remover_quebra_linha(linha);
        c->restaurantes[c->tamanho++] = parse_restaurante(linha);
    }

    fclose(f);
}

Restaurante* buscar_por_id(ColecaoRestaurantes *c,int id){
    for(int i=0;i<c->tamanho;i++)
        if(c->restaurantes[i].id==id)
            return &c->restaurantes[i];
    return NULL;
}

// -------------------------- QUICKSORT --------------------------

long long comparacoes = 0;
long long movimentacoes = 0;

void swap(Restaurante *a,Restaurante *b){
    Restaurante temp=*a;
    *a=*b;
    *b=temp;
    movimentacoes+=3;
}

// compara por avaliacao, desempate por nome
int comparar(Restaurante *a, Restaurante *b){
    comparacoes++;

    if(a->avaliacao < b->avaliacao) return -1;
    if(a->avaliacao > b->avaliacao) return 1;

    comparacoes++;
    return strcmp(a->nome,b->nome);
}

void quicksort(Restaurante arr[],int esq,int dir){
    int i=esq, j=dir;
    Restaurante pivo = arr[(esq+dir)/2];

    while(i<=j){

        while(comparar(&arr[i],&pivo) < 0) i++;
        while(comparar(&arr[j],&pivo) > 0) j--;

        if(i<=j){
            swap(&arr[i],&arr[j]);
            i++;
            j--;
        }
    }

    if(esq<j) quicksort(arr,esq,j);
    if(i<dir) quicksort(arr,i,dir);
}

// -------------------------- MAIN --------------------------

int main(){
    ColecaoRestaurantes c;
    ler_csv(&c);

    Restaurante sel[10000];
    int n=0;

    int id;
    scanf("%d",&id);

    // pega ids e salva restaurantes
    while(id!=-1){
        Restaurante *r = buscar_por_id(&c,id);
        if(r!=NULL) sel[n++]=*r;
        scanf("%d",&id);
    }

    clock_t ini = clock();

    if(n>0)
        quicksort(sel,0,n-1); // ordena por avaliacao

    clock_t fim = clock();

    // imprime
    for(int i=0;i<n;i++){
        char buffer[512];
        formatar_restaurante(&sel[i],buffer);
        printf("%s\n",buffer);
    }

    double tempo = (double)(fim-ini)/CLOCKS_PER_SEC;

    FILE *log = fopen("885005_quicksort.txt","w");
    fprintf(log,"885005\t%lld\t%lld\t%lf\n",comparacoes,movimentacoes,tempo);
    fclose(log);

    return 0;
}
