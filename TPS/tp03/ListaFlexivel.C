#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX 10000

typedef struct
{
    int ano;
    int mes;
    int dia;
} Data;

typedef struct
{
    int hora;
    int minuto;
} Hora;

typedef struct
{
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

// ---------------- PARSE ----------------

Data parse_data(char *s)
{
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

Hora parse_hora(char *s)
{
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

Restaurante *parse_restaurante(char *s)
{
    Restaurante *r = (Restaurante *) malloc(sizeof(Restaurante));

    char tipos[200], preco[20], horario[30], data[30], aberto[20];
    char h1[20], h2[20];

    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%19[^,],%29[^,],%29[^,],%19[^\n\r]",
           &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao,
           tipos, preco, horario, data, aberto);

    int i = 0, j = 0, k = 0;

    while (tipos[i] != '\0')
    {
        if (tipos[i] == ';')
        {
            r->tipos[k][j] = '\0';
            k++;
            j = 0;
        }
        else
        {
            r->tipos[k][j] = tipos[i];
            j++;
        }
        i++;
    }

    r->tipos[k][j] = '\0';
    r->n_tipos = k + 1;

    r->faixa_preco = strlen(preco);

    sscanf(horario, "%[^-]-%s", h1, h2);

    r->abertura = parse_hora(h1);
    r->fechamento = parse_hora(h2);
    r->data = parse_data(data);
    r->aberto = strcmp(aberto, "true") == 0;

    return r;
}

// ---------------- CSV ----------------

void ler_csv(Restaurante *base[], int *quantidade_base)
{
    FILE *arquivo = fopen("/tmp/restaurantes.csv", "r");
    char linha[512];

    *quantidade_base = 0;

    fgets(linha, 512, arquivo);

    while (fgets(linha, 512, arquivo) != NULL)
    {
        base[*quantidade_base] = parse_restaurante(linha);
        (*quantidade_base)++;
    }

    fclose(arquivo);
}

// ---------------- BUSCA ----------------

Restaurante *buscar_por_id(Restaurante *base[], int quantidade_base, int id)
{
    for (int i = 0; i < quantidade_base; i++)
    {
        if (base[i]->id == id)
        {
            return base[i];
        }
    }

    return NULL;
}

// ---------------- FORMATAÇÃO ----------------

void imprimir_restaurante(Restaurante *r)
{
    printf("[%d ## %s ## %s ## %d ## %.1lf ## [",
           r->id, r->nome, r->cidade, r->capacidade, r->avaliacao);

    for (int i = 0; i < r->n_tipos; i++)
    {
        printf("%s", r->tipos[i]);

        if (i < r->n_tipos - 1)
        {
            printf(",");
        }
    }

    printf("] ## ");

    for (int i = 0; i < r->faixa_preco; i++)
    {
        printf("$");
    }

    printf(" ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]\n",
           r->abertura.hora,
           r->abertura.minuto,
           r->fechamento.hora,
           r->fechamento.minuto,
           r->data.dia,
           r->data.mes,
           r->data.ano,
           r->aberto ? "true" : "false");
}

// ---------------- CÉLULA ----------------

typedef struct Celula
{
    Restaurante *elemento;
    struct Celula *prox;
} Celula;

Celula *nova_celula(Restaurante *r)
{
    Celula *nova = (Celula *) malloc(sizeof(Celula));
    nova->elemento = r;
    nova->prox = NULL;
    return nova;
}

// ---------------- LISTA FLEXÍVEL ----------------

typedef struct
{
    Celula *primeiro;
    Celula *ultimo;
    int tamanho;
} Lista;

void iniciar_lista(Lista *lista)
{
    lista->primeiro = nova_celula(NULL); // célula cabeça
    lista->ultimo = lista->primeiro;
    lista->tamanho = 0;
}

// inserir inicio-------------------------------------------------------------------------------------------------------------

void inserir_inicio(Lista *lista, Restaurante *r)
{
    Celula *nova = nova_celula(r);

    nova->prox = lista->primeiro->prox;
    lista->primeiro->prox = nova;

    if (lista->primeiro == lista->ultimo)
    {
        lista->ultimo = nova;
    }

    lista->tamanho++;
}

// inserir fim-------------------------------------------------------------------------------------------------------------

void inserir_fim(Lista *lista, Restaurante *r)
{
    Celula *nova = nova_celula(r);

    lista->ultimo->prox = nova;
    lista->ultimo = nova;

    lista->tamanho++;
}

// inserir em posicao-------------------------------------------------------------------------------------------------------------

void inserir_pos(Lista *lista, Restaurante *r, int pos)
{
    if (pos < 0 || pos > lista->tamanho)
    {
        return;
    }

    if (pos == 0)
    {
        inserir_inicio(lista, r);
    }
    else if (pos == lista->tamanho)
    {
        inserir_fim(lista, r);
    }
    else
    {
        Celula *i = lista->primeiro;

        for (int j = 0; j < pos; j++)
        {
            i = i->prox;
        }

        Celula *nova = nova_celula(r);
        nova->prox = i->prox;
        i->prox = nova;

        lista->tamanho++;
    }
}

// remover inicio-------------------------------------------------------------------------------------------------------------

Restaurante *remover_inicio(Lista *lista)
{
    if (lista->primeiro == lista->ultimo)
    {
        return NULL;
    }

    Celula *tmp = lista->primeiro->prox;
    Restaurante *resp = tmp->elemento;

    lista->primeiro->prox = tmp->prox;

    if (tmp == lista->ultimo)
    {
        lista->ultimo = lista->primeiro;
    }

    tmp->prox = NULL;
    free(tmp);

    lista->tamanho--;

    return resp;
}

// remover fim-------------------------------------------------------------------------------------------------------------

Restaurante *remover_fim(Lista *lista)
{
    if (lista->primeiro == lista->ultimo)
    {
        return NULL;
    }

    Celula *i = lista->primeiro;

    while (i->prox != lista->ultimo)
    {
        i = i->prox;
    }

    Restaurante *resp = lista->ultimo->elemento;

    free(lista->ultimo);

    lista->ultimo = i;
    lista->ultimo->prox = NULL;

    lista->tamanho--;

    return resp;
}

// remover em posicao-------------------------------------------------------------------------------------------------------------

Restaurante *remover_pos(Lista *lista, int pos)
{
    if (pos < 0 || pos >= lista->tamanho)
    {
        return NULL;
    }

    if (pos == 0)
    {
        return remover_inicio(lista);
    }
    else if (pos == lista->tamanho - 1)
    {
        return remover_fim(lista);
    }
    else
    {
        Celula *i = lista->primeiro;

        for (int j = 0; j < pos; j++)
        {
            i = i->prox;
        }

        Celula *tmp = i->prox;
        Restaurante *resp = tmp->elemento;

        i->prox = tmp->prox;

        tmp->prox = NULL;
        free(tmp);

        lista->tamanho--;

        return resp;
    }
}

// mostrar-------------------------------------------------------------------------------------------------------------

void mostrar(Lista *lista)
{
    Celula *i = lista->primeiro->prox;

    while (i != NULL)
    {
        imprimir_restaurante(i->elemento);
        i = i->prox;
    }
}

// liberar lista-------------------------------------------------------------------------------------------------------------

void liberar_lista(Lista *lista)
{
    Celula *i = lista->primeiro;

    while (i != NULL)
    {
        Celula *tmp = i;
        i = i->prox;
        free(tmp);
    }
}

// ---------------- MAIN ----------------

int main()
{
    Restaurante *base[MAX];
    int quantidade_base = 0;

    ler_csv(base, &quantidade_base);

    Lista lista;
    iniciar_lista(&lista);

    int id;
    scanf("%d", &id);

    while (id != -1)
    {
        Restaurante *r = buscar_por_id(base, quantidade_base, id);

        if (r != NULL)
        {
            inserir_fim(&lista, r);
        }

        scanf("%d", &id);
    }

    int quantidade_comandos;
    scanf("%d", &quantidade_comandos);

    for (int i = 0; i < quantidade_comandos; i++)
    {
        char comando[5];
        scanf("%s", comando);

        if (strcmp(comando, "II") == 0)
        {
            int id_inserir;
            scanf("%d", &id_inserir);

            Restaurante *r = buscar_por_id(base, quantidade_base, id_inserir);
            inserir_inicio(&lista, r);
        }
        else if (strcmp(comando, "IF") == 0)
        {
            int id_inserir;
            scanf("%d", &id_inserir);

            Restaurante *r = buscar_por_id(base, quantidade_base, id_inserir);
            inserir_fim(&lista, r);
        }
        else if (strcmp(comando, "I*") == 0)
        {
            int pos, id_inserir;
            scanf("%d %d", &pos, &id_inserir);

            Restaurante *r = buscar_por_id(base, quantidade_base, id_inserir);
            inserir_pos(&lista, r, pos);
        }
        else if (strcmp(comando, "RI") == 0)
        {
            Restaurante *removido = remover_inicio(&lista);
            printf("(R)%s\n", removido->nome);
        }
        else if (strcmp(comando, "RF") == 0)
        {
            Restaurante *removido = remover_fim(&lista);
            printf("(R)%s\n", removido->nome);
        }
        else if (strcmp(comando, "R*") == 0)
        {
            int pos;
            scanf("%d", &pos);

            Restaurante *removido = remover_pos(&lista, pos);
            printf("(R)%s\n", removido->nome);
        }
    }

    mostrar(&lista);

    liberar_lista(&lista);

    for (int i = 0; i < quantidade_base; i++)
    {
        free(base[i]);
    }

    return 0;
}
