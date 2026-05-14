#include <stdio.h>

void proximo(char palavra[], int i) {
    if(palavra[i] == '\n'){
        palavra[i] = '\0';
    }
    if (palavra[i] == '\0') {    //devido ao fgets, o \n vem antes do fim da string
        return;
    }                                                       //verifica se e caracter especial, se nao for adiciona +3, "pulando" 3 caracteres//
        printf("%c", palavra[i] + 3);

    proximo(palavra, i + 1);        // chamada recursiva
}

int main() {
    char texto[1000];

    while (fgets(texto, sizeof(texto), stdin) != NULL) {
        if (texto[0] == 'F' && texto[1] == 'I' && texto[2] == 'M' &&
            (texto[3] == '\n' || texto[3] == '\0')) {
            break;
        }
        proximo(texto, 0);
        printf("\n");
    }

    return 0;
}


