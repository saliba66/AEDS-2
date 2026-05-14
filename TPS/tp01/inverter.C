#include <stdio.h>

void inverter(char palavra[]) {
    char nova[100];
    int tamanho = 0;

    // conta o tamanho da string, sem contar o '\n'
    while (palavra[tamanho] != '\0' && palavra[tamanho] != '\n') {
        tamanho++;
    }

    // monta a string invertida
    for (int j = 0; j < tamanho; j++) {
        nova[j] = palavra[tamanho - 1 - j];
    }

    nova[tamanho] = '\0';

    printf("%s\n", nova);
}

int main() {
    char palavra1[100];

    fgets(palavra1, 100, stdin);

    while (!(palavra1[0] == 'F' &&
             palavra1[1] == 'I' &&
             palavra1[2] == 'M' &&
             (palavra1[3] == '\n' || palavra1[3] == '\0'))) {

        inverter(palavra1);

        fgets(palavra1, 100, stdin);
    }

    return 0;
}
