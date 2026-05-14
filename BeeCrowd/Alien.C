#include <stdio.h>

int main() {
    char vogais[101];
    char frase[101];

    while (fgets(vogais, sizeof(vogais), stdin) != NULL) {
        if (fgets(frase, sizeof(frase), stdin) == NULL) {
            break;
        }

        int cont = 0;

        for (int i = 0; vogais[i] != '\0' && vogais[i] != '\n'; i++) {
            for (int j = 0; vogais[j] != '\0' && vogais[j] != '\n'; j++) {
                if (frase[i] == vogais[j]) {
                    cont++;
                    break;
                }
            }
        }

        printf("%d\n", cont);
    }

    return 0;
}
