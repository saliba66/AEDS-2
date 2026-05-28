#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX 10000

// data -------------------------------------------------------------------------------------------------------------

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

// hora -------------------------------------------------------------------------------------------------------------

typedef struct {
    int hora;
    int minuto;
} Hora;

// restaurante ------------------------------------------------------------------------------------------------------

typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tipos[10][50];
    int n_tipos;
    int faixa_preco;
    Hora abertura;
    Hora fechamento;
    Data data;
    bool aberto;
} Restaurante;

long long comparacoes = 0;
long long movimentacoes = 0;

// parse data -------------------------------------------------------------------------------------------------------

Data parse_data(char *s) {
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

// parse hora -------------------------------------------------------------------------------------------------------

Hora parse_hora(char *s) {
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

// parse restaurante ------------------------------------------------------------------------------------------------

Restaurante *parse_restaurante(char *s) {
    Restaurante *r = (Restaurante *) malloc(sizeof(Restaurante));

    char tipos[200], preco[20], horario[30], data[30], aberto[20];
    char h1[20], h2[20];

    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%19[^,],%29[^,],%29[^,],%19[^\n\r]",
           &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao,
           tipos, preco, horario, data, aberto);

    int i = 0, j = 0, k = 0;

    while (tipos[i] != '\0') {
        if (tipos[i] == ';') {
            r->tipos[k][j] = '\0';
            k++;
            j = 0;
        } else {
            r->tipos[k][j] = tipos[i];
            j++;
        }
        i++;
    }

    r->tipos[k][j] = '\0';
    r->n_tipos = k + 1;

    r->faixa_preco = 0;
    while (preco[r->faixa_preco] != '\0') {
        r->faixa_preco++;
    }

    sscanf(horario, "%[^-]-%s", h1, h2);
    r->abertura = parse_hora(h1);
    r->fechamento = parse_hora(h2);
    r->data = parse_data(data);
    r->aberto = (aberto[0] == 't');

    return r;
}

// formatacao -------------------------------------------------------------------------------------------------------

void formatar(Restaurante *r, char *buffer) {
    char data[20], h1[10], h2[10];

    sprintf(data, "%02d/%02d/%04d", r->data.dia, r->data.mes, r->data.ano);
    sprintf(h1, "%02d:%02d", r->abertura.hora, r->abertura.minuto);
    sprintf(h2, "%02d:%02d", r->fechamento.hora, r->fechamento.minuto);

    int pos = 0;

    pos += sprintf(buffer + pos, "[%d ## %s ## %s ## %d ## %.1lf ## [",
                   r->id, r->nome, r->cidade, r->capacidade, r->avaliacao);

    for (int i = 0; i < r->n_tipos; i++) {
        pos += sprintf(buffer + pos, "%s", r->tipos[i]);
        if (i < r->n_tipos - 1) {
            pos += sprintf(buffer + pos, ",");
        }
    }

    pos += sprintf(buffer + pos, "] ## ");

    for (int i = 0; i < r->faixa_preco; i++) {
        pos += sprintf(buffer + pos, "$");
    }

    pos += sprintf(buffer + pos, " ## %s-%s ## %s ## %s]",
                   h1, h2, data, r->aberto ? "true" : "false");
}

// leitura csv ------------------------------------------------------------------------------------------------------

void ler_csv(Restaurante *base[], int *quantidade_base) {
    FILE *arquivo = fopen("/tmp/restaurantes.csv", "r");
    char linha[512];

    *quantidade_base = 0;

    if (arquivo == NULL) {
        return;
    }

    fgets(linha, 512, arquivo);

    while (fgets(linha, 512, arquivo) != NULL) {
        base[*quantidade_base] = parse_restaurante(linha);
        (*quantidade_base)++;
    }

    fclose(arquivo);
}

// busca por id -----------------------------------------------------------------------------------------------------

Restaurante *buscar_por_id(Restaurante *base[], int quantidade_base, int id) {
    for (int i = 0; i < quantidade_base; i++) {
        if (base[i]->id == id) {
            return base[i];
        }
    }
    return NULL;
}

// celula da lista --------------------------------------------------------------------------------------------------

typedef struct Celula {
    Restaurante *elemento;
    struct Celula *prox;
} Celula;

// lista flexivel ---------------------------------------------------------------------------------------------------

typedef struct {
    Celula *primeiro;
    Celula *ultimo;
} Lista;

// iniciar lista ----------------------------------------------------------------------------------------------------

void iniciar_lista(Lista *lista) {
    lista->primeiro = (Celula *) malloc(sizeof(Celula));
    lista->primeiro->elemento = NULL;
    lista->primeiro->prox = NULL;
    lista->ultimo = lista->primeiro;
}

// inserir no fim ---------------------------------------------------------------------------------------------------

void inserir_fim(Lista *lista, Restaurante *x) {
    Celula *nova = (Celula *) malloc(sizeof(Celula));
    nova->elemento = x;
    nova->prox = NULL;

    lista->ultimo->prox = nova;
    lista->ultimo = nova;
}

// swap de elementos ------------------------------------------------------------------------------------------------

void swap(Restaurante **a, Restaurante **b) {
    Restaurante *tmp = *a;
    *a = *b;
    *b = tmp;
    movimentacoes += 3;
}

// selection sort na lista ------------------------------------------------------------------------------------------

void selecao(Lista *lista) {
    for (Celula *i = lista->primeiro->prox; i != NULL; i = i->prox) {
        Celula *menor = i;

        for (Celula *j = i->prox; j != NULL; j = j->prox) {
            comparacoes++;

            if (strcmp(j->elemento->nome, menor->elemento->nome) < 0) {
                menor = j;
            }
        }

        if (menor != i) {
            swap(&i->elemento, &menor->elemento);
        }
    }
}

// mostrar lista ----------------------------------------------------------------------------------------------------

void mostrar(Lista *lista) {
    Celula *i = lista->primeiro->prox;

    while (i != NULL) {
        char buffer[512];
        formatar(i->elemento, buffer);
        printf("%s\n", buffer);
        i = i->prox;
    }
}

// liberar lista ----------------------------------------------------------------------------------------------------

void liberar_lista(Lista *lista) {
    Celula *i = lista->primeiro;

    while (i != NULL) {
        Celula *tmp = i;
        i = i->prox;
        free(tmp);
    }
}

// main -------------------------------------------------------------------------------------------------------------

int main() {
    Restaurante *base[MAX];
    int quantidade_base = 0;

    ler_csv(base, &quantidade_base);

    Lista lista;
    iniciar_lista(&lista);

    int id;
    scanf("%d", &id);

    while (id != -1) {
        Restaurante *r = buscar_por_id(base, quantidade_base, id);

        if (r != NULL) {
            inserir_fim(&lista, r);
        }

        scanf("%d", &id);
    }

    clock_t inicio = clock();
    selecao(&lista);
    clock_t fim = clock();

    mostrar(&lista);

    double tempo = (double) (fim - inicio) / CLOCKS_PER_SEC;

    FILE *log = fopen("885005_selecao flexivel.txt", "w");
    fprintf(log, "885005\t%lld\t%lld\t%lf\n", comparacoes, movimentacoes, tempo);
    fclose(log);

    liberar_lista(&lista);

    for (int i = 0; i < quantidade_base; i++) {
        free(base[i]);
    }

    return 0;
}
