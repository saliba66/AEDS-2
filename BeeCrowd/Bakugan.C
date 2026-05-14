#include<stdio.h>

int bonus(int jogadas[],int r){
    for (int i = 0; i < r-2; i ++){
    if(jogadas[i] == jogadas[i+1] && jogadas[i+1] == jogadas[i+2]){
        return i;
        }
        }return -1;
    }
void soma(int Marc[],int Let[],int r){
    int M = 0;
    int L = 0;
    for (int i = 0; i < r; i ++){
        M += Marc[i];
        L += Let[i];
    }
     int mbonus = bonus(Marc,r);
     int lbonus = bonus(Let,r);
     if(mbonus<lbonus){
        M+= 30;
     }
     else if(mbonus>lbonus){
     L+= 30;
     }

    if(M > L){
        printf("M\n");
    }
    else if(L>M){
        printf("L\n");
    }
    else if(L == M){
    printf("T\n");
    }
}
int main(){
    int r;
    scanf("%d", &r);
    while(r!=0){
        int Marc[r];
        int Let[r];
        for (int i = 0; i < r; i++)
        {
            scanf("%d", &Marc[i]);
        }
        for (int i = 0; i < r; i++){
         scanf("%d", &Let[i]);
        }
        soma(Marc,Let,r);
    scanf("%d", &r);
}
return 0;
}
