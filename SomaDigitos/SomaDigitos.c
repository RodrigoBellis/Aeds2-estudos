#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// Função recursiva para somar os dígitos
int somaDigitos(int numero) {
    if (numero == 0) {
        return 0;
    } else {
        return (numero % 10) + somaDigitos(numero / 10);
    }
}

int main() {
    char input[100];

    // Lê entradas até encontrar "FIM"
    while (1) {
        scanf("%s", input);

        if (strcmp(input, "FIM") == 0) {
            break; // encerra o loop
        }

        int numero = atoi(input); // converte string para int
        printf("%d\n", somaDigitos(numero));
    }

    return 0;
}
