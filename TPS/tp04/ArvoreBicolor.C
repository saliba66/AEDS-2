#include <stdio.h>
#include <stdlib.h>
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

// no da arvore bicolor ---------------------------------------------------------------------------------------------

typedef struct No {
    Restaurante *elemento;
    struct No *esq;
    struct No *dir;
    bool cor;
} No;

// novo no ----------------------------------------------------------------------------------------------------------

No *novo_no(Restaurante *x) {
    No *no = (No *) malloc(sizeof(No));
    no->elemento = x;
    no->esq = NULL;
    no->dir = NULL;
    no->cor = true;
    return no;
}

// rotacao para direita ---------------------------------------------------------------------------------------------

No *rotacionar_dir(No *no) {
    No *no_esq = no->esq;
    No *no_esq_dir = no_esq->dir;

    no_esq->dir = no;
    no->esq = no_esq_dir;

    return no_esq;
}

// rotacao para esquerda --------------------------------------------------------------------------------------------

No *rotacionar_esq(No *no) {
    No *no_dir = no->dir;
    No *no_dir_esq = no_dir->esq;

    no_dir->esq = no;
    no->dir = no_dir_esq;

    return no_dir;
}

// rotacao direita esquerda -----------------------------------------------------------------------------------------

No *rotacionar_dir_esq(No *no) {
    no->dir = rotacionar_dir(no->dir);
    return rotacionar_esq(no);
}

// rotacao esquerda direita -----------------------------------------------------------------------------------------

No *rotacionar_esq_dir(No *no) {
    no->esq = rotacionar_esq(no->esq);
    return rotacionar_dir(no);
}

// balancear --------------------------------------------------------------------------------------------------------

void balancear(No **raiz, No *bisavo, No *avo, No *pai, No *i) {
    if (pai->cor == true) {
        int cmp_pai_avo = comparar_strings(pai->elemento->nome, avo->elemento->nome);
        int cmp_i_pai = comparar_strings(i->elemento->nome, pai->elemento->nome);

        if (cmp_pai_avo > 0) {
            if (cmp_i_pai > 0) {
                avo = rotacionar_esq(avo);
            } else {
                avo = rotacionar_dir_esq(avo);
            }
        } else {
            if (cmp_i_pai < 0) {
                avo = rotacionar_dir(avo);
            } else {
                avo = rotacionar_esq_dir(avo);
            }
        }

        if (bisavo == NULL) {
            *raiz = avo;
        } else if (comparar_strings(avo->elemento->nome, bisavo->elemento->nome) < 0) {
            bisavo->esq = avo;
        } else {
            bisavo->dir = avo;
        }

        avo->cor = false;
        avo->esq->cor = true;
        avo->dir->cor = true;
    }
}

// inserir recursivo ------------------------------------------------------------------------------------------------

void inserir_rec(No **raiz, Restaurante *x, No *bisavo, No *avo, No *pai, No *i) {
    if (i == NULL) {
        i = novo_no(x);

        if (comparar_strings(x->nome, pai->elemento->nome) < 0) {
            pai->esq = i;
        } else {
            pai->dir = i;
        }

        balancear(raiz, bisavo, avo, pai, i);
    } else {
        if (i->esq != NULL && i->dir != NULL && i->esq->cor == true && i->dir->cor == true) {
            i->cor = true;
            i->esq->cor = false;
            i->dir->cor = false;

            if (i == *raiz) {
                i->cor = false;
            } else {
                balancear(raiz, bisavo, avo, pai, i);
            }
        }

        int cmp = comparar_strings(x->nome, i->elemento->nome);

        if (cmp < 0) {
            inserir_rec(raiz, x, avo, pai, i, i->esq);
        } else if (cmp > 0) {
            inserir_rec(raiz, x, avo, pai, i, i->dir);
        }
    }
}

// inserir ----------------------------------------------------------------------------------------------------------

void inserir(No **raiz, Restaurante *x) {
    if (*raiz == NULL) {
        *raiz = novo_no(x);
    } else if ((*raiz)->esq == NULL && (*raiz)->dir == NULL) {
        if (comparar_strings(x->nome, (*raiz)->elemento->nome) < 0) {
            (*raiz)->esq = novo_no(x);
        } else {
            (*raiz)->dir = novo_no(x);
        }
    } else if ((*raiz)->esq == NULL) {
        if (comparar_strings(x->nome, (*raiz)->elemento->nome) < 0) {
            (*raiz)->esq = novo_no(x);
        } else if (comparar_strings(x->nome, (*raiz)->dir->elemento->nome) < 0) {
            (*raiz)->esq = novo_no((*raiz)->elemento);
            (*raiz)->elemento = x;
        } else {
            (*raiz)->esq = novo_no((*raiz)->elemento);
            (*raiz)->elemento = (*raiz)->dir->elemento;
            (*raiz)->dir->elemento = x;
        }

        (*raiz)->esq->cor = false;
        (*raiz)->dir->cor = false;
    } else if ((*raiz)->dir == NULL) {
        if (comparar_strings(x->nome, (*raiz)->elemento->nome) > 0) {
            (*raiz)->dir = novo_no(x);
        } else if (comparar_strings(x->nome, (*raiz)->esq->elemento->nome) > 0) {
            (*raiz)->dir = novo_no((*raiz)->elemento);
            (*raiz)->elemento = x;
        } else {
            (*raiz)->dir = novo_no((*raiz)->elemento);
            (*raiz)->elemento = (*raiz)->esq->elemento;
            (*raiz)->esq->elemento = x;
        }

        (*raiz)->esq->cor = false;
        (*raiz)->dir->cor = false;
    } else {
        inserir_rec(raiz, x, NULL, NULL, NULL, *raiz);
    }

    (*raiz)->cor = false;
}

// pesquisar --------------------------------------------------------------------------------------------------------

bool pesquisar(No *raiz, char nome[]) {
    if (raiz == NULL) {
        return false;
    }

    comparacoes++;

    int cmp = comparar_strings(nome, raiz->elemento->nome);

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

    // leitura ids --------------------------------------------------------------------------------------------------

    int id;
    scanf("%d", &id);

    while (id != -1) {
        Restaurante *r = buscar_por_id(base, quantidade_base, id);

        if (r != NULL) {
            inserir(&raiz, r);
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

        printf("raiz ");

        if (pesquisar(raiz, nome)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    clock_t fim = clock();
    double tempo = (double) (fim - inicio) / CLOCKS_PER_SEC;

    // mostrar em ordem ---------------------------------------------------------------------------------------------

    mostrar_central(raiz);

    // arquivo log --------------------------------------------------------------------------------------------------

    FILE *log = fopen("885005_alvinegra.txt", "w");
    fprintf(log, "885005\t%lld\t%lf\n", comparacoes, tempo);
    fclose(log);

    liberar_arvore(raiz);

    for (int i = 0; i < quantidade_base; i++) {
        free(base[i]);
    }

    return 0;
}
