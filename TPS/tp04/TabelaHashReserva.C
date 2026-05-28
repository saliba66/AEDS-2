#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

#define MAX 10000
#define TAM_PRINCIPAL 31
#define TAM_RESERVA 19
#define TAM_TABELA 50

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

    *quantidade_base = 0;

    if (arquivo == NULL) {
        arquivo = fopen("../tp02/restaurantes.csv", "r");
    }

    if (arquivo != NULL) {
        fgets(linha, 512, arquivo);

        while (fgets(linha, 512, arquivo) != NULL) {
            base[*quantidade_base] = parse_restaurante(linha);
            (*quantidade_base)++;
        }

        fclose(arquivo);
    }
}

// busca por id -----------------------------------------------------------------------------------------------------

Restaurante *buscar_por_id(Restaurante *base[], int quantidade_base, int id) {
    Restaurante *resp = NULL;

    for (int i = 0; i < quantidade_base; i++) {
        if (base[i]->id == id) {
            resp = base[i];
            i = quantidade_base;
        }
    }

    return resp;
}

// hash -------------------------------------------------------------------------------------------------------------

int hash(Restaurante *r) {
    int soma = 0;

    for (int i = 0; r->nome[i] != '\0'; i++) {
        soma += r->nome[i];
    }

    return soma % TAM_PRINCIPAL;
}

// tabela hash com reserva -----------------------------------------------------------------------------------------

typedef struct {
    Restaurante *tabela[TAM_TABELA];
    int quantidade_reserva;
} HashReserva;

// iniciar tabela ---------------------------------------------------------------------------------------------------

void iniciar(HashReserva *hashReserva) {
    for (int i = 0; i < TAM_TABELA; i++) {
        hashReserva->tabela[i] = NULL;
    }

    hashReserva->quantidade_reserva = 0;
}

// inserir ----------------------------------------------------------------------------------------------------------

void inserir(HashReserva *hashReserva, Restaurante *r) {
    int pos = hash(r);

    if (hashReserva->tabela[pos] == NULL) {
        hashReserva->tabela[pos] = r;
    } else if (hashReserva->quantidade_reserva < TAM_RESERVA) {
        hashReserva->tabela[TAM_PRINCIPAL + hashReserva->quantidade_reserva] = r;
        hashReserva->quantidade_reserva++;
    } else {
        printf("%s\n", r->nome);
    }
}

// pesquisar --------------------------------------------------------------------------------------------------------

int pesquisar(HashReserva *hashReserva, char nome[]) {
    int soma = 0;
    int resp = -1;

    for (int i = 0; nome[i] != '\0'; i++) {
        soma += nome[i];
    }

    int pos = soma % TAM_PRINCIPAL;

    comparacoes++;

    if (hashReserva->tabela[pos] != NULL && comparar_strings(hashReserva->tabela[pos]->nome, nome) == 0) {
        resp = pos;
    } else {
        for (int i = TAM_PRINCIPAL; i < TAM_PRINCIPAL + hashReserva->quantidade_reserva; i++) {
            comparacoes++;

            if (hashReserva->tabela[i] != NULL && comparar_strings(hashReserva->tabela[i]->nome, nome) == 0) {
                resp = i;
                i = TAM_PRINCIPAL + hashReserva->quantidade_reserva;
            }
        }
    }

    return resp;
}

// main -------------------------------------------------------------------------------------------------------------

int main() {
    Restaurante *base[MAX];
    int quantidade_base = 0;

    ler_csv(base, &quantidade_base);

    HashReserva hashReserva;
    iniciar(&hashReserva);

    int id;
    scanf("%d", &id);

    while (id != -1) {
        Restaurante *r = buscar_por_id(base, quantidade_base, id);

        if (r != NULL) {
            inserir(&hashReserva, r);
        }

        scanf("%d", &id);
    }

    char nome[100];

    clock_t inicio = clock();

    while (scanf(" %[^\n\r]", nome) == 1) {
        if (comparar_strings(nome, "FIM") == 0) {
            break;
        }

        int pos = pesquisar(&hashReserva, nome);

        if (pos == -1) {
            printf("-1\n");
        } else {
            char buffer[512];
            formatar(hashReserva.tabela[pos], buffer);
            printf("%d %s\n", pos, buffer);
        }
    }

    clock_t fim = clock();
    double tempo = (double) (fim - inicio) / CLOCKS_PER_SEC;

    FILE *log = fopen("885005_hash_reserva.txt", "w");
    fprintf(log, "885005\t%lld\t%lf\n", comparacoes, tempo);
    fclose(log);

    for (int i = 0; i < quantidade_base; i++) {
        free(base[i]);
    }

    return 0;
}
