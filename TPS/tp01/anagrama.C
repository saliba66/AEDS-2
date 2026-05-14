#include <stdio.h>
#include <stdbool.h>

bool comparar(char p1[], char p2[]) {
    int t1 = 0, t2 = 0;

    while (p1[t1] != '\0') {
        t1++;
    }
    while (p2[t2] != '\0') {
        t2++;
    }

    if (t1 != t2) return false;

    for (int i = 0; i < t1; i++) {
        bool achou = false;

        for (int j = 0; j < t2; j++) {
            if (p1[i] == p2[j]) {
                p2[j] = '*'; // marca como usada
                achou = true;
                break;
            }
        }

        if (!achou) return false;
    }

    return true;
}

int main() {
    char palavra1[100], palavra2[100];
    scanf("%s", palavra1);

    while (!(palavra1[0] == 'F' && palavra1[1] == 'I' && palavra1[2] == 'M' && palavra1[3] == '\0')) {
        scanf("%s", palavra2);
        // converter palavra1 para minúsculo
        for (int i = 0; palavra1[i] != '\0'; i++) {
            if (palavra1[i] >= 'A' && palavra1[i] <= 'Z') {
                palavra1[i] = palavra1[i] + 32;
            }
        }
        // converter palavra2 para minúsculo
        for (int i = 0; palavra2[i] != '\0'; i++) {
            if (palavra2[i] >= 'A' && palavra2[i] <= 'Z') {
                palavra2[i] = palavra2[i] + 32;
            }
        }
        if (comparar(palavra1, palavra2)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
        scanf("%s", palavra1);
    }

    return 0;
}
