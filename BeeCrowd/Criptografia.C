#include <stdio.h>
#include <string.h>

int main() {
    char frase[300];

    while (fgets(frase, sizeof(frase), stdin) != NULL) {
        int tam = strlen(frase);

        if (tam > 0 && frase[tam - 1] == '\n') {
            frase[tam - 1] = '\0';
        }

        for (int i = 0; frase[i] != '\0'; i++) {
            if (frase[i] == '@') frase[i] = 'a';
            else if (frase[i] == '&') frase[i] = 'e';
            else if (frase[i] == '!') frase[i] = 'i';
            else if (frase[i] == '*') frase[i] = 'o';
            else if (frase[i] == '#') frase[i] = 'u';
        }

        printf("%s\n", frase);
    }

    return 0;
}

