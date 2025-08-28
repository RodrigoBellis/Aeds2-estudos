#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include <stdbool.h>

// Verifica se a string tem só vogais
bool SoVogal(char palavra[]) {
    for (int i = 0; palavra[i] != '\0'; i++) {
        char letra = tolower(palavra[i]);
        if (letra != 'a' && letra != 'e' && letra != 'i' && letra != 'o' && letra != 'u') {
            return false;
        }
    }
    return true;
}

// Verifica se a string tem só consoantes
bool SoConsoante(char palavra[]) {
    for (int i = 0; palavra[i] != '\0'; i++) {
        char letra = tolower(palavra[i]);
        if (letra < 'a' || letra > 'z') {
            return false;
        }
        if (letra == 'a' || letra == 'e' || letra == 'i' || letra == 'o' || letra == 'u') {
            return false;
        }
    }
    return true;
}

// Verifica se a string é inteiro
bool EhInteiro(char palavra[]) {
    int i = 0;
    if (palavra[0] == '-' || palavra[0] == '+') i++;
    for (; palavra[i] != '\0'; i++) {
        if (!isdigit(palavra[i])) {
            return false;
        }
    }
    return (i > 0);
}

// Verifica se a string é real
bool EhReal(char palavra[]) {
    int i = 0, ponto = 0, digitos = 0;
    if (palavra[0] == '-' || palavra[0] == '+') i++;
    for (; palavra[i] != '\0'; i++) {
        if (palavra[i] == '.' || palavra[i] == ',') {
            ponto++;
            if (ponto > 1) return false;
        } else if (!isdigit(palavra[i])) {
            return false;
        } else {
            digitos++;
        }
    }
    return (digitos > 0);
}

int main() {
    char palavra[100];

    while (1) {
        fgets(palavra, sizeof(palavra), stdin);
        palavra[strcspn(palavra, "\n")] = '\0'; // remove \n

        if (strcmp(palavra, "FIM") == 0) break;

        printf("%s ", SoVogal(palavra) ? "SIM" : "NAO");
        printf("%s ", SoConsoante(palavra) ? "SIM" : "NAO");
        printf("%s ", EhInteiro(palavra) ? "SIM" : "NAO");
        printf("%s\n", EhReal(palavra) ? "SIM" : "NAO");
    }

    return 0;
}
