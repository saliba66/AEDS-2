#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

typedef struct
{
    int dia, mes, ano;
} Data;

typedef struct
{
    int hora, minuto;
} Hora;

typedef struct
{
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tiposCozinha[20][50];
    int qtdTipos;
    int faixaPreco;
    Hora horarioAbertura;
    Hora horarioFechamento;
    Data dataAbertura;
    bool aberto;
} Restaurante;

typedef struct
{
    Restaurante restaurantes[1000];
    int tamanho;
} ColecaoRestaurantes;

long comparacoes = 0;
long movimentacoes = 0;

Data parseData(char *s)
{
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

Hora parseHora(char *s)
{
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

void parseRestaurante(char *linha, Restaurante *r)
{
    char *campos[10];
    int i = 0;

    char *token = strtok(linha, ",");

    while (token != NULL && i < 10)
    {
        campos[i++] = token;
        token = strtok(NULL, ",");
    }

    r->id = atoi(campos[0]);
    strcpy(r->nome, campos[1]);
    strcpy(r->cidade, campos[2]);
    r->capacidade = atoi(campos[3]);
    r->avaliacao = atof(campos[4]);

    r->qtdTipos = 0;
    char tipos[300];
    strcpy(tipos, campos[5]);

    char *tipo = strtok(tipos, ";");

    while (tipo != NULL)
    {
        strcpy(r->tiposCozinha[r->qtdTipos], tipo);
        r->qtdTipos++;
        tipo = strtok(NULL, ";");
    }

    r->faixaPreco = strlen(campos[6]);

    char horario[50];
    strcpy(horario, campos[7]);

    char *abertura = strtok(horario, "-");
    char *fechamento = strtok(NULL, "-");

    r->horarioAbertura = parseHora(abertura);
    r->horarioFechamento = parseHora(fechamento);

    r->dataAbertura = parseData(campos[8]);

    campos[9][strcspn(campos[9], "\n\r")] = '\0';
    r->aberto = strcmp(campos[9], "true") == 0;
}

void lerCsv(ColecaoRestaurantes *colecao, char *path)
{
    FILE *file = fopen(path, "r");

    if (file != NULL)
    {
        char linha[1000];

        fgets(linha, sizeof(linha), file);

        while (fgets(linha, sizeof(linha), file) != NULL)
        {
            parseRestaurante(linha, &colecao->restaurantes[colecao->tamanho]);
            colecao->tamanho++;
        }

        fclose(file);
    }
}

Restaurante buscarPorId(ColecaoRestaurantes *colecao, int id)
{
    Restaurante resp;
    resp.id = -1;

    for (int i = 0; i < colecao->tamanho; i++)
    {
        if (colecao->restaurantes[i].id == id)
        {
            resp = colecao->restaurantes[i];
            i = colecao->tamanho;
        }
    }

    return resp;
}

int compararData(Data a, Data b)
{
    if (a.ano != b.ano)
        return a.ano - b.ano;
    if (a.mes != b.mes)
        return a.mes - b.mes;
    return a.dia - b.dia;
}

int comparar(Restaurante a, Restaurante b)
{
    comparacoes++;

    int resp = compararData(a.dataAbertura, b.dataAbertura);

    if (resp == 0)
    {
        resp = strcmp(a.nome, b.nome);
    }

    return resp;
}

void swap(Restaurante array[], int i, int j)
{
    Restaurante tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
    movimentacoes += 3;
}

void heapifyMax(Restaurante array[], int tam, int i)
{
    int maior = i;
    int esq = 2 * i + 1;
    int dir = 2 * i + 2;

    if (esq < tam && comparar(array[esq], array[maior]) > 0)
    {
        maior = esq;
    }

    if (dir < tam && comparar(array[dir], array[maior]) > 0)
    {
        maior = dir;
    }

    if (maior != i)
    {
        swap(array, i, maior);
        heapifyMax(array, tam, maior);
    }
}

void construirHeapMax(Restaurante array[], int tam)
{
    for (int i = tam / 2 - 1; i >= 0; i--)
    {
        heapifyMax(array, tam, i);
    }
}

void heapsortParcial(Restaurante array[], int n)
{
    int k = 10;

    if (n < 10)
    {
        k = n;
    }

    construirHeapMax(array, k);

    for (int i = k; i < n; i++)
    {
        if (comparar(array[i], array[0]) < 0)
        {
            swap(array, i, 0);
            heapifyMax(array, k, 0);
        }
    }

    for (int tam = k; tam > 1; tam--)
    {
        swap(array, 0, tam - 1);
        heapifyMax(array, tam - 1, 0);
    }
}

void imprimirRestaurante(Restaurante r)
{
    printf("[%d ## %s ## %s ## %d ## %.1lf ## [",
           r.id, r.nome, r.cidade, r.capacidade, r.avaliacao);

    for (int i = 0; i < r.qtdTipos; i++)
    {
        printf("%s", r.tiposCozinha[i]);

        if (i < r.qtdTipos - 1)
        {
            printf(",");
        }
    }

    printf("] ## ");

    for (int i = 0; i < r.faixaPreco; i++)
    {
        printf("$");
    }

    printf(" ## %02d:%02d-%02d:%02d ## %02d/%02d/%04d ## %s]\n",
           r.horarioAbertura.hora,
           r.horarioAbertura.minuto,
           r.horarioFechamento.hora,
           r.horarioFechamento.minuto,
           r.dataAbertura.dia,
           r.dataAbertura.mes,
           r.dataAbertura.ano,
           r.aberto ? "true" : "false");
}

void gerarLog(double tempo)
{
    FILE *log = fopen("885156_heapsort_parcial.txt", "w");

    if (log != NULL)
    {
        fprintf(log, "885156\t%ld\t%ld\t%lf\n",
                comparacoes, movimentacoes, tempo);
        fclose(log);
    }
}

int main()
{
    ColecaoRestaurantes colecao;
    colecao.tamanho = 0;

    lerCsv(&colecao, "/tmp/restaurantes.csv");

    Restaurante array[1000];
    int n = 0;

    char entrada[50];

    while (scanf("%s", entrada) == 1 &&
           strcmp(entrada, "FIM") != 0 &&
           strcmp(entrada, "-1") != 0)
    {

        int id = atoi(entrada);

        Restaurante r = buscarPorId(&colecao, id);

        if (r.id != -1)
        {
            array[n] = r;
            n++;
        }
    }

    clock_t inicio = clock();

    heapsortParcial(array, n);

    clock_t fim = clock();

    for (int i = 0; i < n; i++)
    {
        imprimirRestaurante(array[i]);
    }

    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    gerarLog(tempo);

    return 0;
}