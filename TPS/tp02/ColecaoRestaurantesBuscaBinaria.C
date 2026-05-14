#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX 10000

typedef struct {
    int ano, mes, dia;
} Data;

typedef struct {
    int hora, minuto;
} Hora;

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

// remove \n e \r das strings lidas
void limpar_linha(char texto[]) {
    int i = 0;
    while (texto[i] != '\0') {
        if (texto[i] == '\n' || texto[i] == '\r') {
            texto[i] = '\0';
        }
        i++;
    }
}

// ---------------- PARSE ----------------

Data parse_data(char *s) {
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

Hora parse_hora(char *s) {
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

Restaurante* parse_restaurante(char *s) {
    Restaurante *r = (Restaurante*) malloc(sizeof(Restaurante));

    char tipos[200], preco[10], horario[20], data[20], aberto[10];
    char h1[10], h2[10];

    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9[^\n\r]",
        &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao,
        tipos, preco, horario, data, aberto);

    int i = 0, j = 0, k = 0;

    while (tipos[i] != '\0') {
        if (tipos[i] == ';') {
            r->tipos[k][j] = '\0';
            k++;
            j = 0;
        } else {
            r->tipos[k][j++] = tipos[i];
        }
        i++;
    }

    r->tipos[k][j] = '\0';
    r->n_tipos = k + 1;

    r->faixa_preco = 0;
    for (i = 0; preco[i] != '\0'; i++) {
        r->faixa_preco++;
    }

    sscanf(horario, "%[^-]-%s", h1, h2);
    r->abertura = parse_hora(h1);
    r->fechamento = parse_hora(h2);

    r->data = parse_data(data);
    r->aberto = aberto[0] == 't';

    return r;
}

// ---------------- CSV ----------------

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

// busca restaurante pelo id dentro da base completa
Restaurante* buscar_por_id(Restaurante *base[], int quantidade_base, int id_procurado) {
    for (int i = 0; i < quantidade_base; i++) {
        if (base[i]->id == id_procurado) {
            return base[i];
        }
    }
    return NULL;
}

// ---------------- ORDENAÇÃO ----------------

void swap(Restaurante **a, Restaurante **b) {
    Restaurante *tmp = *a;
    *a = *b;
    *b = tmp;
}

// ordena por nome para permitir pesquisa binária
void selection_sort(Restaurante *array[], int quantidade) {
    for (int i = 0; i < quantidade - 1; i++) {
        int menor = i;

        for (int j = i + 1; j < quantidade; j++) {
            if (strcmp(array[j]->nome, array[menor]->nome) < 0) {
                menor = j;
            }
        }

        if (menor != i) {
            swap(&array[i], &array[menor]);
        }
    }
}

// ---------------- PESQUISA BINÁRIA ----------------

bool pesquisa_binaria(Restaurante *array[], int quantidade, char nome_pesquisado[]) {
    int esquerda = 0;
    int direita = quantidade - 1;

    while (esquerda <= direita) {
        int meio = (esquerda + direita) / 2;

        comparacoes++;
        int resultado = strcmp(nome_pesquisado, array[meio]->nome);

        if (resultado == 0) {
            return true;
        } else if (resultado > 0) {
            esquerda = meio + 1;
        } else {
            direita = meio - 1;
        }
    }

    return false;
}

// ---------------- MAIN ----------------

int main() {
    Restaurante *base_restaurantes[MAX];
    Restaurante *restaurantes_selecionados[MAX];

    int quantidade_base = 0;
    int quantidade_selecionados = 0;

    ler_csv(base_restaurantes, &quantidade_base);

    // primeira parte da entrada: ids dos restaurantes
    int id_lido;
    scanf("%d", &id_lido);

    while (id_lido != -1) {
        Restaurante *restaurante = buscar_por_id(base_restaurantes, quantidade_base, id_lido);

        if (restaurante != NULL) {
            restaurantes_selecionados[quantidade_selecionados] = restaurante;
            quantidade_selecionados++;
        }

        scanf("%d", &id_lido);
    }

    // ordena os selecionados por nome antes da pesquisa binária
    selection_sort(restaurantes_selecionados, quantidade_selecionados);

    char nome_pesquisado[100];

    clock_t inicio = clock();

    // segunda parte da entrada: nomes pesquisados até FIM
    while (scanf(" %[^\n]", nome_pesquisado) == 1) {
        limpar_linha(nome_pesquisado);

        if (strcmp(nome_pesquisado, "FIM") == 0) {
            break;
        }

        if (pesquisa_binaria(restaurantes_selecionados, quantidade_selecionados, nome_pesquisado)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    clock_t fim = clock();

    double tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;

    FILE *log = fopen("885005_binaria.txt", "w");
    fprintf(log, "885005\t%lld\t%lf\n", comparacoes, tempo);
    fclose(log);

    for (int i = 0; i < quantidade_base; i++) {
        free(base_restaurantes[i]);
    }

    return 0;
}
