#include <stdio.h>

int tamanho(char s[]) {
    int tam = 0;
    while (s[tam] != '\0') {
        tam++;
    }
    return tam;
}

int maior(char s[]) {
    int n = tamanho(s);
    int maior = 0;
    for (int i = 0; i < n; i++) {               //utiliza 3 fors, o de fora vai andando com o caractere na string testando as posibilidades, ele anda so com a 1 letra da string, podemos falar assim
        int cont = 0;                           //o for de dentro testa todas as substrings a,ab,abc,abca, depois testa comecando com b,bc,bca
        for (int j = i; j < n; j++) {
            int repetiu = 0;
            for (int k = i; k < j; k++) {          //esse for verifica se a letra ja foi utilizada entao se meu j é 3, K vai olhar a posicao 0,1,2 e ver
                                                    //se algum já foi usado
                if (s[k] == s[j]) {
                    repetiu = 1;
                }
            }
            if (repetiu == 1) {
                break;
            }
            cont++;
        }
        if (cont > maior) {
            maior = cont;
        }
    }

    return maior;
}

int main() {
    char palavra1[1000];
    scanf("%s", palavra1);
    while(!(palavra1[0] == 'F' && palavra1[1] == 'I' && palavra1[2] == 'M' &&  palavra1[3] == '\0')){
        int m = maior(palavra1);
        printf("%d\n",m);
    scanf("%s", palavra1);
        }

    return 0;
}
