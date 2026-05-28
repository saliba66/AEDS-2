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

// no da arvore -----------------------------------------------------------------------------------------------------

typedef struct No {
    Restaurante *elemento;
    struct No *esq;
    struct No *dir;
} No;

// novo no ----------------------------------------------------------------------------------------------------------

No *novo_no(Restaurante *x) {
    No *no = (No *) malloc(sizeof(No));
    no->elemento = x;
    no->esq = NULL;
    no->dir = NULL;
    return no;
}

// inserir ----------------------------------------------------------------------------------------------------------

No *inserir(No *raiz, Restaurante *x) {
    if (raiz == NULL) {
        return novo_no(x);
    }

    int cmp = strcmp(x->nome, raiz->elemento->nome);

    if (cmp < 0) {
        raiz->esq = inserir(raiz->esq, x);
    } else if (cmp > 0) {
        raiz->dir = inserir(raiz->dir, x);
    }

    return raiz;
}

// pesquisar --------------------------------------------------------------------------------------------------------

bool pesquisar(No *raiz, char nome[]) {
    if (raiz == NULL) {
        return false;
    }

    comparacoes++;

    int cmp = strcmp(nome, raiz->elemento->nome);

    if (cmp == 0) {
        return true;
    } else if (cmp < 0) {
        printf("esq ");
        return pesquisar(raiz->esq, nome);
    } else {
        printf("dir ");
        return pesquisar(raiz->dir, nome);
    }
}

// mostrar em ordem -------------------------------------------------------------------------------------------------

void mostrar_central(No *raiz) {
    if (raiz != NULL) {
        mostrar_central(raiz->esq);

        char buffer[512];
        formatar(raiz->elemento, buffer);
        printf("%s\n", buffer);

        mostrar_central(raiz->dir);
    }
}

// liberar arvore ---------------------------------------------------------------------------------------------------

void liberar_arvore(No *raiz) {
    if (raiz != NULL) {
        liberar_arvore(raiz->esq);
        liberar_arvore(raiz->dir);
        free(raiz);
    }
}

// main -------------------------------------------------------------------------------------------------------------

int main() {
    Restaurante *base[MAX];
    int quantidade_base = 0;

    ler_csv(base, &quantidade_base);

    No *raiz = NULL;

    int id;
    scanf("%d", &id);

    while (id != -1) {
        Restaurante *r = buscar_por_id(base, quantidade_base, id);

        if (r != NULL) {
            raiz = inserir(raiz, r);
        }

        scanf("%d", &id);
    }

    char nome[100];

    clock_t inicio = clock();

    while (scanf(" %[^\n\r]", nome) == 1) {
        if (strcmp(nome, "FIM") == 0) {
            break;
        }

        printf("raiz ");

        if (pesquisar(raiz, nome)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    clock_t fim = clock();

    mostrar_central(raiz);

    double tempo = (double) (fim - inicio) / CLOCKS_PER_SEC;

    FILE *log = fopen("885005_arvore binaria.txt", "w");
    fprintf(log, "885005\t%lld\t%lf\n", comparacoes, tempo);
    fclose(log);

    liberar_arvore(raiz);

    for (int i = 0; i < quantidade_base; i++) {
        free(base[i]);
    }

    return 0;
}
