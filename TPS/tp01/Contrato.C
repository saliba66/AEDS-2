#include <stdio.h>

int main() {
    char falho;
    char numero[105];

    scanf(" %c %s", &falho, numero);

    while (!(falho == '0' && numero[0] == '0' && numero[1] == '\0')) {
        int k = 0;
        char novo[105];
        for (int i = 0; numero[i] != '\0'; i++) {
            if (numero[i] != falho) {
                novo[k] = numero[i];
                k++;
            }
        }
        novo[k] = '\0';
        int n = 0;
        while (n < k && novo[n] == '0') {
            n++;
        }

        if (n == k) {
            printf("0\n");
        } else {
            printf("%s\n", novo + n);
        }

        scanf(" %c %s", &falho, numero);
    }

    return 0;
}
