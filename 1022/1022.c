#include<stdio.h>
#include<stdlib.h>

// Função para calcular o MDC (Máximo Divisor Comum)
int mdc(int a, int b) {
    if (b == 0)
        return a;
    return mdc(b, a % b);
}

int main(){
    int ct;
    float n1, n2, d1, d2, resultadonum[100], resultadoden[100];
    char operador;
    char expressao[100];
    int num[100], den[100];

    printf("Digite a quantidade de caso testes que deseja? ");
    scanf("%d", &ct);
    getchar();

    for(int i = 0; i < ct; i++){
        printf("Digite a expressao: ");
        fgets(expressao, sizeof(expressao), stdin);

        if(sscanf(expressao, "%f / %f %c %f / %f", &n1, &d1, &operador, &n2, &d2) == 5) {
            switch (operador){
                case '+':
                    resultadonum[i] = n1 * d2 + n2 * d1;
                    resultadoden[i] = d1 * d2;
                    break;
                case '-': 
                    resultadonum[i] = n1 * d2 - n2 * d1;
                    resultadoden[i] = d1 * d2;
                    break;
                case '*':
                    resultadonum[i] = n1 * n2;
                    resultadoden[i] = d1 * d2;
                    break;
                case '/':
                    resultadonum[i] = n1 * d2;
                    resultadoden[i] = d1 * n2;
                    break;
                default:
                    printf("Nao existe esse operador!! Invalido!!\n");
                    i--;
                    continue;
            }

            num[i] = (int)resultadonum[i];
            den[i] = (int)resultadoden[i];
            int divisor = mdc(abs(num[i]), abs(den[i]));
            num[i] /= divisor;
            den[i] /= divisor;
        } else {
            printf("ERRO!!\n");
            i--;
        }
    }

    // Exibe todos os resultados ao final
    for(int i = 0; i < ct; i++) {
        printf("resultado%d: %.0f / %.0f = %d / %d\n", i+1, resultadonum[i], resultadoden[i], num[i], den[i]);
    }

    return 0;
}