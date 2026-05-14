#include <stdio.h>
#include <string.h>

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int hora;
    int minuto;
} Hora;

typedef struct {
    int id;
    char nome[100];
    char cidade[100];
    int capacidade;
    double avaliacao;
    char tipos_cozinha[10][50];
    int n_tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura;
    Hora horario_fechamento;
    Data data_abertura;
    int aberto;
} Restaurante;

typedef struct {
    int tamanho;
    Restaurante restaurantes[10000];
} ColecaoRestaurantes;

// ---------------------------------- DATA ----------------------------------

Data parse_data(char *s) {
    Data data;
    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia);
    return data;
}

void formatar_data(Data *data, char *buffer) {
    int pos = 0;

    if (data->dia < 10)
        pos += sprintf(buffer + pos, "0%d", data->dia);
    else
        pos += sprintf(buffer + pos, "%d", data->dia);

    pos += sprintf(buffer + pos, "/");

    if (data->mes < 10)
        pos += sprintf(buffer + pos, "0%d", data->mes);
    else
        pos += sprintf(buffer + pos, "%d", data->mes);

    pos += sprintf(buffer + pos, "/%04d", data->ano);
}

// ---------------------------------- HORA ----------------------------------

Hora parse_hora(char *s) {
    Hora hora;
    sscanf(s, "%d:%d", &hora.hora, &hora.minuto);
    return hora;
}

void formatar_hora(Hora *hora, char *buffer) {
    int pos = 0;

    if (hora->hora < 10)                    //verifica se a hora é menor que 10 para a formatacao do 0 na frente 09, 10, 11
        pos += sprintf(buffer + pos, "0%d", hora->hora);
    else
        pos += sprintf(buffer + pos, "%d", hora->hora);

    pos += sprintf(buffer + pos, ":");

    if (hora->minuto < 10)
        pos += sprintf(buffer + pos, "0%d", hora->minuto);                  //faz o mesmo com minutos e segundos
    else
        pos += sprintf(buffer + pos, "%d", hora->minuto);
}

// ------------------------------ RESTAURANTE -------------------------------

Restaurante parse_restaurante(char *s) {
    Restaurante restaurante;

    char tipos[200];
    char faixa_preco_str[10];
    char horario[20];
    char data_str[20];
    char aberto_str[10];

    char hora_abertura_str[10];
    char hora_fechamento_str[10];

    sscanf(s,"%d,%99[^,],%99[^,],%d,%lf,%199[^,],%9[^,],%19[^,],%19[^,],%9[^\n]",&restaurante.id,restaurante.nome,restaurante.cidade,&restaurante.capacidade,&restaurante.avaliacao,tipos,faixa_preco_str,horario,data_str,aberto_str
    );

    // tipos cozinha
    restaurante.n_tipos_cozinha = 0;
    int i = 0, j = 0, k = 0;

    while (tipos[i] != '\0') {
        if (tipos[i] == ';') {
            restaurante.tipos_cozinha[k][j] = '\0';
            k++;
            j = 0;
        } else {
            restaurante.tipos_cozinha[k][j] = tipos[i];      //com nao existe String[]tipos em C,  temos que tratar como "matriz", o vetor para conseguir salvar na ordem correta
            j++;
        }
        i++;
    }
    restaurante.tipos_cozinha[k][j] = '\0';
    restaurante.n_tipos_cozinha = k + 1;

    // faixa preco
    restaurante.faixa_preco = 0;
    i = 0;
    while (faixa_preco_str[i] != '\0') {
        restaurante.faixa_preco++;
        i++;
    }

    // horario
    sscanf(horario, "%[^-]-%s", hora_abertura_str, hora_fechamento_str);    //faz essa leitura para conseguir separar os horarios com o -
    restaurante.horario_abertura = parse_hora(hora_abertura_str);
    restaurante.horario_fechamento = parse_hora(hora_fechamento_str);

    // data
    restaurante.data_abertura = parse_data(data_str);

    // aberto
   if (aberto_str[0] == 't')
    restaurante.aberto = 1;
else
    restaurante.aberto = 0;
    return restaurante;
}

void formatar_restaurante(Restaurante *r, char *buffer) {
    char data_buffer[20];
    char hora_abertura_buffer[10];
    char hora_fechamento_buffer[10];
    formatar_data(&r->data_abertura, data_buffer);
    formatar_hora(&r->horario_abertura, hora_abertura_buffer);
    formatar_hora(&r->horario_fechamento, hora_fechamento_buffer);

    int pos = 0;

    pos += sprintf(buffer + pos, "[%d ## %s ## %s ## %d ## %.1lf ## [",r->id, r->nome, r->cidade, r->capacidade, r->avaliacao);   //formata no modelo da saida

    for (int i = 0; i < r->n_tipos_cozinha; i++) {
        pos += sprintf(buffer + pos, "%s", r->tipos_cozinha[i]);          //formata a parte dos tipos que é diferente, gracas aos ()
        if (i < r->n_tipos_cozinha - 1)
            pos += sprintf(buffer + pos, ",");
    }

    pos += sprintf(buffer + pos, "] ## ");

    for (int i = 0; i < r->faixa_preco; i++) {
        pos += sprintf(buffer + pos, "$");             //preço com $
    }

    pos += sprintf(buffer + pos, " ## %s-%s ## %s ## %s]",hora_abertura_buffer,hora_fechamento_buffer,data_buffer,r->aberto ? "true" : "false");         //ternario com o boleano
}

// -------------------------- COLECAO RESTAURANTES --------------------------

void ler_csv_colecao(ColecaoRestaurantes *colecao, char *path) {
    FILE *arquivo = fopen(path, "r");
    char linha[512];
    colecao->tamanho = 0;
    if (arquivo == NULL) return;
    fgets(linha, sizeof(linha), arquivo);
    while (fgets(linha, sizeof(linha), arquivo) != NULL) {
        colecao->restaurantes[colecao->tamanho] = parse_restaurante(linha);
        colecao->tamanho++;
    }

    fclose(arquivo);
}

ColecaoRestaurantes ler_csv() {
    ColecaoRestaurantes colecao;
    ler_csv_colecao(&colecao, "/tmp/restaurantes.csv");
    return colecao;
}

Restaurante* buscar_por_id(ColecaoRestaurantes *colecao, int id) {
    for (int i = 0; i < colecao->tamanho; i++) {
        if (colecao->restaurantes[i].id == id)
            return &colecao->restaurantes[i];
    }
    return NULL;
}




//-------------------------------------SELECTION SORT-----------------------------------------
int comparacoes = 0;
int movimentacoes = 0;

void swap(Restaurante *a, Restaurante *b) {             //metodo swap padrao
    Restaurante temp = *a;
    *a = *b;
    *b = temp;
    movimentacoes += 3;
}
void selecao(Restaurante arr[], int n) {                  //nosso selection sort comparando nomes
    for (int i = 0; i < n - 1; i++) {
        int menor = i;
        for (int j = i + 1; j < n; j++) {
            comparacoes++;
            if (strcmp(arr[j].nome, arr[menor].nome) < 0) {
                menor = j;
            }
        }
        if (menor != i) {
            swap(&arr[i], &arr[menor]);
        }
    }
}

// ---------------------------------- MAIN ----------------------------------
int main() {
    ColecaoRestaurantes colecao = ler_csv();
    Restaurante selecionados[1000];
    int n = 0;
    int id;
    scanf("%d", &id);
    while (id != -1) {
        Restaurante *r = buscar_por_id(&colecao, id);     // cria a variavel da struct restaurante, e chama a funcao para procurar o id digitado, retorna um restaurante
        if (r != NULL) {
            selecionados[n] = *r;
            n++;
        }
        scanf("%d", &id);
    }

    long inicio = clock();
    selecao(selecionados, n);                   //chama selection sort com os restaurantes especificos e a quantidada deles(n)
    long fim = clock();
    for (int i = 0; i < n; i++) {
        char buffer[512];
        formatar_restaurante(&selecionados[i], buffer); //cria o buffer para armazenar as informacoes de X restaurante, assim printamos o buffer, que vai exibir as informações

        printf("%s\n", buffer);
    }
    double tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;           //calcula o tempo
    FILE *log = fopen("885005_selecao.txt", "w");                       //cria arquivo com a matricula
    fprintf(log, "885005\t%d\t%d\t%lf", comparacoes, movimentacoes, tempo);         //matricula, numero de comparacoes, de movimentacoes e o tempo
    fclose(log);
    return 0;
}




