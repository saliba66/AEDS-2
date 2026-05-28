#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

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

long long comparacoes = 0;
long long movimentacoes = 0;

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
    Restaurante *r = (Restaurante *)malloc(sizeof(Restaurante));

    char tipos[200];
    char preco[20];
    char horario[30];
    char data[30];
    char aberto[20];
    char h1[20];
    char h2[20];

    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%19[^,],%29[^,],%29[^,],%19[^\n\r]",
           &r->id,
           r->nome,
           r->cidade,
           &r->capacidade,
           &r->avaliacao,
           tipos,
           preco,
           horario,
           data,
           aberto);

    int i = 0;
    int j = 0;
    int k = 0;

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

    r->aberto = (strcmp(aberto, "true") == 0);

    return r;
}

// ---------------- CSV ----------------

void ler_csv(Restaurante *base[], int *quantidade_base)
{
    FILE *arquivo = fopen("/tmp/restaurantes.csv", "r");
    char linha[512];

    *quantidade_base = 0;

    fgets(linha, 512, arquivo); // pula cabeçalho

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
           r->id,
           r->nome,
           r->cidade,
           r->capacidade,
           r->avaliacao);

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

// ---------------- INSERÇÃO PARCIAL ----------------
// Ordenação por cidade crescente.
// Em caso de empate, mantém a ordem original da entrada.

int comparar(Restaurante *a, Restaurante *b)
{
    comparacoes++;
    return strcmp(a->cidade, b->cidade);
}

void insercao_parcial(Restaurante *array[], int n)
{
    int k = 10;

    if (n < k)
    {
        k = n;
    }

    // primeiro ordena os k primeiros normalmente
    for (int i = 1; i < k; i++)
    {
        Restaurante *tmp = array[i];
        movimentacoes++;

        int j = i - 1;

        while (j >= 0 && comparar(array[j], tmp) > 0)
        {
            array[j + 1] = array[j];
            movimentacoes++;
            j--;
        }

        array[j + 1] = tmp;
        movimentacoes++;
    }

    // se algum elemento for menor que o maior dos 10 primeiros,
    // ele entra na parte ordenada e o antigo 10º vai para a posição i
    for (int i = k; i < n; i++)
    {
        if (comparar(array[i], array[k - 1]) < 0)
        {
            Restaurante *tmp = array[i];
            Restaurante *sobra = array[k - 1];
            movimentacoes += 2;

            int j = k - 2;

            while (j >= 0 && comparar(array[j], tmp) > 0)
            {
                array[j + 1] = array[j];
                movimentacoes++;
                j--;
            }

            array[j + 1] = tmp;
            array[i] = sobra;
            movimentacoes += 2;
        }
    }
}

// ---------------- MAIN ----------------

int main()
{
    Restaurante *base[MAX];
    Restaurante *selecionados[MAX];

    int quantidade_base = 0;
    int n = 0;

    ler_csv(base, &quantidade_base);

    int id;
    scanf("%d", &id);

    while (id != -1)
    {
        Restaurante *r = buscar_por_id(base, quantidade_base, id);

        if (r != NULL)
        {
            selecionados[n] = r;
            n++;
        }

        scanf("%d", &id);
    }

    clock_t inicio = clock();

    insercao_parcial(selecionados, n);

    clock_t fim = clock();

    for (int i = 0; i < n; i++)
    {
        imprimir_restaurante(selecionados[i]);
    }

    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    FILE *log = fopen("885005_insercao_parcial.txt", "w");
    fprintf(log, "885005\t%lld\t%lld\t%lf\n", comparacoes, movimentacoes, tempo);
    fclose(log);

    for (int i = 0; i < quantidade_base; i++)
    {
        free(base[i]);
    }

    return 0;
}
