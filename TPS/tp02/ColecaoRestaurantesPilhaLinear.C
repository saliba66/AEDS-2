#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_RESTAURANTES 10000

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
    int n_tipos;
    int faixa_preco;
    Hora abertura;
    Hora fechamento;
    Data data;
    bool aberto;
} Restaurante;

// ---------------- PARSE ----------------

Data parse_data(char *s){
    Data d;
    sscanf(s,"%d-%d-%d",&d.ano,&d.mes,&d.dia);
    return d;
}

Hora parse_hora(char *s){
    Hora h;
    sscanf(s,"%d:%d",&h.hora,&h.minuto);
    return h;
}

Restaurante *parse_restaurante(char *s){
    Restaurante *r = (Restaurante *) malloc(sizeof(Restaurante));

    char tipos[200], preco[10], horario[20], data[20], aberto[10];
    char h1[10], h2[10];

    sscanf(s,"%d,%99[^,],%99[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9[^\n\r]",
        &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao,
        tipos, preco, horario, data, aberto);

    // tipos
    int i = 0, j = 0, k = 0;
    while(tipos[i] != '\0'){
        if(tipos[i] == ';'){
            r->tipos_cozinha[k][j] = '\0';
            k++;
            j = 0;
        } else {
            r->tipos_cozinha[k][j] = tipos[i];
            j++;
        }
        i++;
    }
    r->tipos_cozinha[k][j] = '\0';
    r->n_tipos = k + 1;

    // faixa preco
    r->faixa_preco = 0;
    i = 0;
    while(preco[i] != '\0'){
        r->faixa_preco++;
        i++;
    }

    // horario
    sscanf(horario,"%[^-]-%s",h1,h2);
    r->abertura = parse_hora(h1);
    r->fechamento = parse_hora(h2);

    // data
    r->data = parse_data(data);

    // aberto
    r->aberto = (aberto[0] == 't');

    return r;
}

// ---------------- FORMATAR ----------------

void formatar(Restaurante *r, char *buffer){
    char data[20], h1[10], h2[10];

    sprintf(data,"%02d/%02d/%04d", r->data.dia, r->data.mes, r->data.ano);
    sprintf(h1,"%02d:%02d", r->abertura.hora, r->abertura.minuto);
    sprintf(h2,"%02d:%02d", r->fechamento.hora, r->fechamento.minuto);

    int pos = 0;

    pos += sprintf(buffer + pos,"[%d ## %s ## %s ## %d ## %.1lf ## [",
        r->id, r->nome, r->cidade, r->capacidade, r->avaliacao);

    for(int i = 0; i < r->n_tipos; i++){
        pos += sprintf(buffer + pos,"%s", r->tipos_cozinha[i]);
        if(i < r->n_tipos - 1){
            pos += sprintf(buffer + pos,",");
        }
    }

    pos += sprintf(buffer + pos,"] ## ");

    for(int i = 0; i < r->faixa_preco; i++){
        pos += sprintf(buffer + pos,"$");
    }

    pos += sprintf(buffer + pos," ## %s-%s ## %s ## %s]",
        h1, h2, data, r->aberto ? "true" : "false");
}

// ---------------- CSV ----------------

void ler_csv(Restaurante *base_restaurantes[], int *quantidade_base){
    FILE *arquivo = fopen("/tmp/restaurantes.csv","r");
    char linha[512];

    *quantidade_base = 0;

    if(arquivo == NULL){
        return;
    }

    fgets(linha, 512, arquivo);

    while(fgets(linha, 512, arquivo) != NULL){
        base_restaurantes[*quantidade_base] = parse_restaurante(linha);
        (*quantidade_base)++;
    }

    fclose(arquivo);
}

Restaurante *buscar(Restaurante *base_restaurantes[], int quantidade_base, int id_procurado){
    for(int i = 0; i < quantidade_base; i++){
        if(base_restaurantes[i]->id == id_procurado){
            return base_restaurantes[i];
        }
    }
    return NULL;
}

// ---------------- PILHA ----------------

typedef struct {
    Restaurante *array[MAX_RESTAURANTES];
    int topo;
} PilhaRestaurantes;

void iniciar_pilha(PilhaRestaurantes *pilha){
    pilha->topo = 0;
}

// empilha no topo
void inserir(PilhaRestaurantes *pilha, Restaurante *restaurante){
    pilha->array[pilha->topo] = restaurante;
    pilha->topo++;
}

// desempilha do topo
Restaurante *remover(PilhaRestaurantes *pilha){
    pilha->topo--;
    return pilha->array[pilha->topo];
}

// mostra a pilha a partir do topo
void mostrar(PilhaRestaurantes *pilha){
    for(int i = pilha->topo - 1; i >= 0; i--){
        char buffer[512];
        formatar(pilha->array[i], buffer);
        printf("%s\n", buffer);
    }
}

// ---------------- MAIN ----------------

int main(){
    Restaurante *base_restaurantes[MAX_RESTAURANTES];
    int quantidade_base = 0;

    ler_csv(base_restaurantes, &quantidade_base);

    PilhaRestaurantes pilha;
    iniciar_pilha(&pilha);

    int id_lido;

    // lê os ids iniciais até -1
    scanf("%d", &id_lido);

    while(id_lido != -1){
        Restaurante *restaurante = buscar(base_restaurantes, quantidade_base, id_lido);

        if(restaurante != NULL){
            inserir(&pilha, restaurante);
        }

        scanf("%d", &id_lido);
    }

    int quantidade_comandos;
    scanf("%d", &quantidade_comandos);

    for(int i = 0; i < quantidade_comandos; i++){
        char comando[5];
        scanf("%s", comando);

        if(comando[0] == 'I'){
            int id_restaurante;
            scanf("%d", &id_restaurante);

            Restaurante *restaurante = buscar(base_restaurantes, quantidade_base, id_restaurante);

            if(restaurante != NULL){
                inserir(&pilha, restaurante);
            }

        } else if(comando[0] == 'R'){
            Restaurante *removido = remover(&pilha);
            printf("(R)%s\n", removido->nome);
        }
    }

    mostrar(&pilha);

    for(int i = 0; i < quantidade_base; i++){
        free(base_restaurantes[i]);
    }

    return 0;
}
