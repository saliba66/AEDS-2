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

// declaracao do no da trie -----------------------------------------------------------------------------------------

typedef struct NoTrie NoTrie;

// celula da lista de filhos ----------------------------------------------------------------------------------------

typedef struct CelulaFilho {
    NoTrie *elemento;
    struct CelulaFilho *prox;
} CelulaFilho;

// lista flexivel de filhos -----------------------------------------------------------------------------------------

typedef struct {
    CelulaFilho *primeiro;
    CelulaFilho *ultimo;
} ListaFilhos;

// no da trie -------------------------------------------------------------------------------------------------------

struct NoTrie {
    char letra;
    bool folha;
    Restaurante *elemento;
    ListaFilhos filhos;
};

// nova celula de filho ---------------------------------------------------------------------------------------------

CelulaFilho *nova_celula_filho(NoTrie *x) {
    CelulaFilho *nova = (CelulaFilho *) malloc(sizeof(CelulaFilho));
    nova->elemento = x;
    nova->prox = NULL;
    return nova;
}

// iniciar lista de filhos ------------------------------------------------------------------------------------------

void iniciar_lista_filhos(ListaFilhos *lista) {
    lista->primeiro = nova_celula_filho(NULL);
    lista->ultimo = lista->primeiro;
}

// novo no da trie --------------------------------------------------------------------------------------------------

NoTrie *novo_no_trie(char letra) {
    NoTrie *novo = (NoTrie *) malloc(sizeof(NoTrie));
    novo->letra = letra;
    novo->folha = false;
    novo->elemento = NULL;
    iniciar_lista_filhos(&novo->filhos);
    movimentacoes++;
    return novo;
}

// inserir filho ----------------------------------------------------------------------------------------------------

void inserir_filho(ListaFilhos *lista, NoTrie *filho) {
    CelulaFilho *nova = nova_celula_filho(filho);

    lista->ultimo->prox = nova;
    lista->ultimo = nova;
}

// buscar filho -----------------------------------------------------------------------------------------------------

NoTrie *buscar_filho(NoTrie *no, char letra) {
    NoTrie *resp = NULL;
    CelulaFilho *i = no->filhos.primeiro->prox;

    while (i != NULL && resp == NULL) {
        comparacoes++;

        if (i->elemento->letra == letra) {
            resp = i->elemento;
        }

        i = i->prox;
    }

    return resp;
}

// arvore trie ------------------------------------------------------------------------------------------------------

typedef struct {
    NoTrie *raiz;
} ArvoreTrie;

// iniciar trie -----------------------------------------------------------------------------------------------------

void iniciar_trie(ArvoreTrie *trie) {
    trie->raiz = novo_no_trie('\0');
}

// inserir trie -----------------------------------------------------------------------------------------------------

void inserir_trie(ArvoreTrie *trie, Restaurante *x) {
    NoTrie *i = trie->raiz;
    int pos = 0;

    while (x->nome[pos] != '\0') {
        NoTrie *prox = buscar_filho(i, x->nome[pos]);

        if (prox == NULL) {
            prox = novo_no_trie(x->nome[pos]);
            inserir_filho(&i->filhos, prox);
        }

        i = prox;
        pos++;
    }

    i->folha = true;
    i->elemento = x;
}

// pesquisar trie ---------------------------------------------------------------------------------------------------

Restaurante *pesquisar_trie(ArvoreTrie *trie, char nome[]) {
    Restaurante *resp = NULL;
    NoTrie *i = trie->raiz;
    int pos = 0;
    bool continuar = true;

    while (nome[pos] != '\0' && continuar) {
        NoTrie *prox = buscar_filho(i, nome[pos]);

        if (prox == NULL) {
            continuar = false;
        } else {
            printf("%c ", nome[pos]);
            i = prox;
            pos++;
        }
    }

    if (continuar && nome[pos] == '\0' && i->folha) {
        resp = i->elemento;
    }

    return resp;
}

// mostrar pesquisa -------------------------------------------------------------------------------------------------

void mostrar_pesquisa(ArvoreTrie *trie, char nome[]) {
    Restaurante *resp = pesquisar_trie(trie, nome);

    if (resp == NULL) {
        printf("NAO\n");
    } else {
        char buffer[512];
        formatar(resp, buffer);
        printf("SIM %s\n", buffer);
    }
}

// liberar lista de filhos ------------------------------------------------------------------------------------------

void liberar_lista_filhos(ListaFilhos *lista) {
    CelulaFilho *i = lista->primeiro;

    while (i != NULL) {
        CelulaFilho *tmp = i;
        i = i->prox;
        free(tmp);
    }
}

// liberar trie -----------------------------------------------------------------------------------------------------

void liberar_trie(NoTrie *no) {
    if (no != NULL) {
        CelulaFilho *i = no->filhos.primeiro->prox;

        while (i != NULL) {
            liberar_trie(i->elemento);
            i = i->prox;
        }

        liberar_lista_filhos(&no->filhos);
        free(no);
    }
}

// main -------------------------------------------------------------------------------------------------------------

int main() {
    Restaurante *base[MAX];
    int quantidade_base = 0;

    ler_csv(base, &quantidade_base);

    ArvoreTrie trie;
    iniciar_trie(&trie);

    // leitura ids --------------------------------------------------------------------------------------------------

    int id;
    scanf("%d", &id);

    while (id != -1) {
        Restaurante *r = buscar_por_id(base, quantidade_base, id);

        if (r != NULL) {
            inserir_trie(&trie, r);
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

        mostrar_pesquisa(&trie, nome);
    }

    clock_t fim = clock();
    double tempo = (double) (fim - inicio) / CLOCKS_PER_SEC;

    // arquivo log --------------------------------------------------------------------------------------------------

    FILE *log = fopen("885005_trie_lista.txt", "w");
    fprintf(log, "885005\t%lld\t%lld\t%lf\n", comparacoes, movimentacoes, tempo);
    fclose(log);

    liberar_trie(trie.raiz);

    for (int i = 0; i < quantidade_base; i++) {
        free(base[i]);
    }

    return 0;
}
