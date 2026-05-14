#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

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
    int n_tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura;
    Hora horario_fechamento;
    Data data_abertura;
    bool aberto;
} Restaurante;

typedef struct {
    int tamanho;
    Restaurante *restaurantes[MAX_RESTAURANTES];
} ColecaoRestaurantes;

// ---------------------------------- DATA ----------------------------------

Data parse_data(char *s) {
    Data data;
    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia);
    return data;
}

void formatar_data(Data *data, char *buffer) {
    sprintf(buffer, "%02d/%02d/%04d", data->dia, data->mes, data->ano);
}

// ---------------------------------- HORA ----------------------------------

Hora parse_hora(char *s) {
    Hora hora;
    sscanf(s, "%d:%d", &hora.hora, &hora.minuto);
    return hora;
}

void formatar_hora(Hora *hora, char *buffer) {
    sprintf(buffer, "%02d:%02d", hora->hora, hora->minuto);
}

// ------------------------------ RESTAURANTE -------------------------------

Restaurante *parse_restaurante(char *s) {
    Restaurante *r = (Restaurante *) malloc(sizeof(Restaurante));

    char tipos[200], preco[10], horario[20], data[20], aberto[10];
    char h1[10], h2[10];

    sscanf(s,"%d,%99[^,],%99[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9[^\n\r]",
           &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao,
           tipos, preco, horario, data, aberto);

    // tipos cozinha
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
    r->n_tipos_cozinha = k + 1;

    // faixa preco
    r->faixa_preco = 0;
    i = 0;
    while(preco[i] != '\0'){
        r->faixa_preco++;
        i++;
    }

    // horario
    sscanf(horario,"%[^-]-%s",h1,h2);
    r->horario_abertura = parse_hora(h1);
    r->horario_fechamento = parse_hora(h2);

    // data
    r->data_abertura = parse_data(data);

    // aberto
    r->aberto = (aberto[0] == 't');

    return r;
}

void formatar_restaurante(Restaurante *r, char *buffer){
    char data[20], h1[10], h2[10];

    formatar_data(&r->data_abertura, data);
    formatar_hora(&r->horario_abertura, h1);
    formatar_hora(&r->horario_fechamento, h2);

    int pos = 0;

    pos += sprintf(buffer + pos,"[%d ## %s ## %s ## %d ## %.1lf ## [",
        r->id, r->nome, r->cidade, r->capacidade, r->avaliacao);

    for(int i = 0; i < r->n_tipos_cozinha; i++){
        pos += sprintf(buffer + pos,"%s", r->tipos_cozinha[i]);
        if(i < r->n_tipos_cozinha - 1){
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

// -------------------------- COLECAO --------------------------

void ler_csv_colecao(ColecaoRestaurantes *colecao){
    FILE *arquivo = fopen("/tmp/restaurantes.csv","r");
    char linha[512];

    colecao->tamanho = 0;

    if(arquivo == NULL){
        return;
    }

    fgets(linha, sizeof(linha), arquivo);

    while(fgets(linha, sizeof(linha), arquivo) != NULL){
        colecao->restaurantes[colecao->tamanho] = parse_restaurante(linha);
        colecao->tamanho++;
    }

    fclose(arquivo);
}

Restaurante *buscar_por_id(ColecaoRestaurantes *colecao, int id){
    for(int i = 0; i < colecao->tamanho; i++){
        if(colecao->restaurantes[i]->id == id){
            return colecao->restaurantes[i];
        }
    }
    return NULL;
}

void liberar_colecao(ColecaoRestaurantes *colecao){
    for(int i = 0; i < colecao->tamanho; i++){
        free(colecao->restaurantes[i]);
    }
}

//-------------------------------------COUNTING SORT-----------------------------------------

long long comparacoes = 0;
long long movimentacoes = 0;

void counting_sort(Restaurante *array[], int quantidade){
    if(quantidade <= 0){
        return;
    }

    int maior_capacidade = array[0]->capacidade;

    for(int i = 1; i < quantidade; i++){
        comparacoes++;
        if(array[i]->capacidade > maior_capacidade){
            maior_capacidade = array[i]->capacidade;
        }
    }

    int *contador = (int *) malloc((maior_capacidade + 1) * sizeof(int));
    Restaurante **ordenado = (Restaurante **) malloc(quantidade * sizeof(Restaurante *));

    for(int i = 0; i <= maior_capacidade; i++){
        contador[i] = 0;
    }

    for(int i = 0; i < quantidade; i++){
        contador[array[i]->capacidade]++;
    }

    for(int i = 1; i <= maior_capacidade; i++){
        contador[i] += contador[i - 1];
    }

    for(int i = quantidade - 1; i >= 0; i--){
        int posicao = contador[array[i]->capacidade] - 1;
        ordenado[posicao] = array[i];
        contador[array[i]->capacidade]--;
        movimentacoes++;
    }

    for(int i = 0; i < quantidade; i++){
        array[i] = ordenado[i];
        movimentacoes++;
    }

    free(contador);
    free(ordenado);
}

// ---------------------------------- MAIN ----------------------------------

int main(){
    ColecaoRestaurantes colecao;
    Restaurante *restaurantes_selecionados[MAX_RESTAURANTES];

    int quantidade_selecionados = 0;
    int id_lido;

    ler_csv_colecao(&colecao);

    // lê os ids da entrada até encontrar -1
    scanf("%d", &id_lido);

    while(id_lido != -1){
        Restaurante *restaurante = buscar_por_id(&colecao, id_lido);

        if(restaurante != NULL){
            restaurantes_selecionados[quantidade_selecionados] = restaurante;
            quantidade_selecionados++;
        }

        scanf("%d", &id_lido);
    }

    clock_t inicio = clock();

    if(quantidade_selecionados > 0){
        counting_sort(restaurantes_selecionados, quantidade_selecionados);
    }

    clock_t fim = clock();

    // imprime os restaurantes ordenados
    for(int i = 0; i < quantidade_selecionados; i++){
        char buffer[512];
        formatar_restaurante(restaurantes_selecionados[i], buffer);
        printf("%s\n", buffer);
    }

    double tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;

    FILE *log = fopen("885005_countingsort.txt","w");
    fprintf(log,"885005\t%lld\t%lld\t%lf\n", comparacoes, movimentacoes, tempo);
    fclose(log);

    liberar_colecao(&colecao);

    return 0;
}
