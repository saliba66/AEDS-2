#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

#define MAX 10000
#define TAM 31

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

// comparar strings -------------------------------------------------------------------------------------------------

int comparar_strings(char a[], char b[]) {
    int i = 0;

    while (a[i] != '\0' && b[i] != '\0' && a[i] == b[i]) {
        i++;
    }

    return a[i] - b[i];
}

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

    char tipos[200];
    char preco[20];
    char horario[30];
    char data[30];
    char aberto[20];
    char h1[20];
    char h2[20];

    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%19[^,],%29[^,],%29[^,],%19[^\n\r]",
           &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao,
           tipos, preco, horario, data, aberto);

    int i = 0;
    int j = 0;
    int k = 0;

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
    char data[20];
    char h1[10];
    char h2[10];
    char *aberto;

    sprintf(data, "%02d/%02d/%04d", r->data.dia, r->data.mes, r->data.ano);
    sprintf(h1, "%02d:%02d", r->abertura.hora, r->abertura.minuto);
    sprintf(h2, "%02d:%02d", r->fechamento.hora, r->fechamento.minuto);

    if (r->aberto) {
        aberto = "true";
    } else {
        aberto = "false";
    }

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
                   h1, h2, data, aberto);
}

// leitura csv ------------------------------------------------------------------------------------------------------

void ler_csv(Restaurante *base[], int *quantidade_base) {
    FILE *arquivo = fopen("/tmp/restaurantes.csv", "r");
    char linha[512];

    if (arquivo == NULL) {
        arquivo = fopen("../tp02/restaurantes.csv", "r");
    }

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

// nova celula ------------------------------------------------------------------------------------------------------

Celula *nova_celula(Restaurante *x) {
    Celula *nova = (Celula *) malloc(sizeof(Celula));
    nova->elemento = x;
    nova->prox = NULL;
    return nova;
}

// lista simples ----------------------------------------------------------------------------------------------------

typedef struct {
    Celula *primeiro;
    Celula *ultimo;
} Lista;

// iniciar lista ----------------------------------------------------------------------------------------------------

void iniciar_lista(Lista *lista) {
    lista->primeiro = nova_celula(NULL);
    lista->ultimo = lista->primeiro;
}

// inserir no fim ---------------------------------------------------------------------------------------------------

void inserir_fim(Lista *lista, Restaurante *x) {
    Celula *nova = nova_celula(x);

    lista->ultimo->prox = nova;
    lista->ultimo = nova;
    movimentacoes++;
}

// pesquisar lista --------------------------------------------------------------------------------------------------

Restaurante *pesquisar_lista(Lista *lista, char nome[]) {
    Restaurante *resp = NULL;
    Celula *i = lista->primeiro->prox;

    while (i != NULL && resp == NULL) {
        comparacoes++;

        if (comparar_strings(i->elemento->nome, nome) == 0) {
            resp = i->elemento;
        }

        i = i->prox;
    }

    return resp;
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

// tabela hash indireta ---------------------------------------------------------------------------------------------

typedef struct {
    Lista tabela[TAM];
} TabelaHash;

// iniciar tabela ---------------------------------------------------------------------------------------------------

void iniciar_tabela(TabelaHash *hash) {
    for (int i = 0; i < TAM; i++) {
        iniciar_lista(&hash->tabela[i]);
    }
}

// soma dos caracteres do nome --------------------------------------------------------------------------------------

int soma_caracteres(char nome[]) {
    int soma = 0;

    for (int i = 0; nome[i] != '\0'; i++) {
        soma += nome[i];
    }

    return soma;
}

// funcao hash ------------------------------------------------------------------------------------------------------

int funcao_hash(char nome[]) {
    return soma_caracteres(nome) % TAM;
}

// inserir hash -----------------------------------------------------------------------------------------------------

void inserir_hash(TabelaHash *hash, Restaurante *x) {
    if (x != NULL) {
        int pos = funcao_hash(x->nome);
        inserir_fim(&hash->tabela[pos], x);
    }
}

// pesquisar hash ---------------------------------------------------------------------------------------------------

Restaurante *pesquisar_hash(TabelaHash *hash, char nome[], int *pos) {
    *pos = funcao_hash(nome);
    return pesquisar_lista(&hash->tabela[*pos], nome);
}

// mostrar pesquisa -------------------------------------------------------------------------------------------------

void mostrar_pesquisa(TabelaHash *hash, char nome[]) {
    int pos;
    Restaurante *resp = pesquisar_hash(hash, nome, &pos);

    if (resp == NULL) {
        printf("-1\n");
    } else {
        char buffer[512];
        formatar(resp, buffer);
        printf("%d %s\n", pos, buffer);
    }
}

// liberar tabela ---------------------------------------------------------------------------------------------------

void liberar_tabela(TabelaHash *hash) {
    for (int i = 0; i < TAM; i++) {
        liberar_lista(&hash->tabela[i]);
    }
}

// main -------------------------------------------------------------------------------------------------------------

int main() {
    Restaurante *base[MAX];
    int quantidade_base = 0;

    ler_csv(base, &quantidade_base);

    TabelaHash hash;
    iniciar_tabela(&hash);

    // leitura ids --------------------------------------------------------------------------------------------------

    int id;
    scanf("%d", &id);

    while (id != -1) {
        Restaurante *r = buscar_por_id(base, quantidade_base, id);

        if (r != NULL) {
            inserir_hash(&hash, r);
        }

        scanf("%d", &id);
    }

    char nome[100];
    clock_t inicio = clock();

    // pesquisas ----------------------------------------------------------------------------------------------------

    while (scanf(" %[^\n\r]", nome) == 1) {
        if (comparar_strings(nome, "FIM") == 0) {
            break;
        }

        mostrar_pesquisa(&hash, nome);
    }

    clock_t fim = clock();
    double tempo = (double) (fim - inicio) / CLOCKS_PER_SEC;

    // arquivo log --------------------------------------------------------------------------------------------------

    FILE *log = fopen("885005_hash_indireta.txt", "w");
    fprintf(log, "885005\t%lld\t%lld\t%lf\n", comparacoes, movimentacoes, tempo);
    fclose(log);

    liberar_tabela(&hash);

    for (int i = 0; i < quantidade_base; i++) {
        free(base[i]);
    }

    return 0;
}
