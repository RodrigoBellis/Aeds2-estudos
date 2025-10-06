#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_STR 5000
#define MAX_ARRAY 50

typedef struct {
    int appId;
    char name[MAX_STR];
    char releaseDate[20];
    int owners;
    float price;
    char languages[MAX_ARRAY][MAX_STR];
    int languagesCount;
    int metascore;
    float userScore;
    int achievements;
    char publishers[MAX_ARRAY][MAX_STR];
    int publishersCount;
    char developers[MAX_ARRAY][MAX_STR];
    int developersCount;
    char categories[MAX_ARRAY][MAX_STR];
    int categoriesCount;
    char genres[MAX_ARRAY][MAX_STR];
    int genresCount;
    char tags[MAX_ARRAY][MAX_STR];
    int tagsCount;
} Game;

// ---------- Funções Auxiliares ----------

void trim(char *str) {
    if (!str) return;
    int len = strlen(str);
    while (len > 0 && (str[len - 1] == '\n' || str[len - 1] == '\r' || str[len - 1] == ' ')) {
        str[--len] = '\0';
    }
    int start = 0;
    while (str[start] == ' ') start++;
    if (start > 0)
        memmove(str, str + start, strlen(str + start) + 1);
}

void splitList(const char *src, char dest[MAX_ARRAY][MAX_STR], int *count) {
    *count = 0;
    if (!src || strlen(src) < 3) return;

    char buffer[MAX_STR];
    strcpy(buffer, src);

    // Remove aspas e colchetes
    if (buffer[0] == '"') memmove(buffer, buffer + 1, strlen(buffer));
    if (buffer[strlen(buffer) - 1] == '"') buffer[strlen(buffer) - 1] = '\0';
    if (buffer[0] == '[') memmove(buffer, buffer + 1, strlen(buffer));
    if (buffer[strlen(buffer) - 1] == ']') buffer[strlen(buffer) - 1] = '\0';

    // Remove aspas simples
    for (int i = 0; buffer[i]; i++)
        if (buffer[i] == '\'') buffer[i] = ' ';

    // Quebra por vírgula
    char *token = strtok(buffer, ",");
    while (token && *count < MAX_ARRAY) {
        trim(token);
        strcpy(dest[*count], token);
        (*count)++;
        token = strtok(NULL, ",");
    }
}

void formatarData(char *data) {
    if (data[0] == '"') memmove(data, data + 1, strlen(data));
    if (data[strlen(data) - 1] == '"') data[strlen(data) - 1] = '\0';

    char mes[4], dia[4], ano[6];
    sscanf(data, "%3s %2[^,], %4s", mes, dia, ano);

    char mesNum[3];
    if (strcmp(mes, "Jan") == 0) strcpy(mesNum, "01");
    else if (strcmp(mes, "Feb") == 0) strcpy(mesNum, "02");
    else if (strcmp(mes, "Mar") == 0) strcpy(mesNum, "03");
    else if (strcmp(mes, "Apr") == 0) strcpy(mesNum, "04");
    else if (strcmp(mes, "May") == 0) strcpy(mesNum, "05");
    else if (strcmp(mes, "Jun") == 0) strcpy(mesNum, "06");
    else if (strcmp(mes, "Jul") == 0) strcpy(mesNum, "07");
    else if (strcmp(mes, "Aug") == 0) strcpy(mesNum, "08");
    else if (strcmp(mes, "Sep") == 0) strcpy(mesNum, "09");
    else if (strcmp(mes, "Oct") == 0) strcpy(mesNum, "10");
    else if (strcmp(mes, "Nov") == 0) strcpy(mesNum, "11");
    else if (strcmp(mes, "Dec") == 0) strcpy(mesNum, "12");
    else strcpy(mesNum, "00");

    sprintf(data, "%s/%s/%s", dia, mesNum, ano);
}

void initGame(Game *g) {
    g->appId = 0;
    strcpy(g->name, "NaN");
    strcpy(g->releaseDate, "NaN");
    g->owners = 0;
    g->price = 0.0;
    g->languagesCount = 0;
    g->metascore = -1;
    g->userScore = 0.0;
    g->achievements = 0;
    g->publishersCount = 0;
    g->developersCount = 0;
    g->categoriesCount = 0;
    g->genresCount = 0;
    g->tagsCount = 0;
}

// ---------- Parsing CSV ----------

void parseGame(Game *g, const char *line) {
    char buffer[MAX_STR * 2];
    strcpy(buffer, line);

    char *tokens[20];
    int tokenCount = 0;
    char *ptr = buffer;
    bool inQuotes = false;
    char *start = buffer;

    for (; *ptr; ptr++) {
        if (*ptr == '"') inQuotes = !inQuotes;
        else if (*ptr == ',' && !inQuotes) {
            *ptr = '\0';
            tokens[tokenCount++] = start;
            start = ptr + 1;
        }
    }
    tokens[tokenCount++] = start;

    for (int i = 0; i < tokenCount; i++) trim(tokens[i]);

    g->appId = atoi(tokens[0]);
    strcpy(g->name, strlen(tokens[1]) ? tokens[1] : "NaN");
    strcpy(g->releaseDate, strlen(tokens[2]) ? tokens[2] : "NaN");
    formatarData(g->releaseDate);
    g->owners = atoi(tokens[3]);
    g->price = atof(tokens[4]);
    splitList(tokens[5], g->languages, &g->languagesCount);
    g->metascore = strlen(tokens[6]) ? atoi(tokens[6]) : 0;
    g->userScore = (strcmp(tokens[7], "tbd") != 0) ? atof(tokens[7]) : 0.0;
    g->achievements = strlen(tokens[8]) ? atoi(tokens[8]) : 0;
    splitList(tokens[9], g->publishers, &g->publishersCount);
    splitList(tokens[10], g->developers, &g->developersCount);
    splitList(tokens[11], g->categories, &g->categoriesCount);
    splitList(tokens[12], g->genres, &g->genresCount);
    splitList(tokens[13], g->tags, &g->tagsCount);
}

// ---------- Impressão Formatada ----------

void printGame(const Game *g) {
    printf("=> %d ## %s ## %s ## %d ## %.2f ## [", g->appId, g->name, g->releaseDate, g->owners, g->price);

    for (int i = 0; i < g->languagesCount; i++)
        printf("%s%s", g->languages[i], (i < g->languagesCount - 1) ? ", " : "");

    printf("] ## %d ## %.1f ## %d ## [", g->metascore, g->userScore, g->achievements);

    for (int i = 0; i < g->publishersCount; i++)
        printf("%s%s", g->publishers[i], (i < g->publishersCount - 1) ? ", " : "");

    printf("] ## [");
    for (int i = 0; i < g->developersCount; i++)
        printf("%s%s", g->developers[i], (i < g->developersCount - 1) ? ", " : "");

    printf("] ## [");
    for (int i = 0; i < g->categoriesCount; i++)
        printf("%s%s", g->categories[i], (i < g->categoriesCount - 1) ? ", " : "");

    printf("] ## [");
    for (int i = 0; i < g->genresCount; i++)
        printf("%s%s", g->genres[i], (i < g->genresCount - 1) ? ", " : "");

    printf("] ## [");
    for (int i = 0; i < g->tagsCount; i++)
        printf("%s%s", g->tags[i], (i < g->tagsCount - 1) ? ", " : "");

    printf("] ##\n");
}

// ---------- Principal ----------

int main() {
    const char *arquivo = "/tmp/games.csv";
    char entrada[50];

    while (true) {
        fgets(entrada, sizeof(entrada), stdin);
        entrada[strcspn(entrada, "\n")] = '\0';
        if (strcmp(entrada, "FIM") == 0) break;

        int idBusca = atoi(entrada);
        FILE *file = fopen(arquivo, "r");
        if (!file) {
            perror("Erro ao abrir o arquivo");
            return 1;
        }

        char line[MAX_STR];
        fgets(line, sizeof(line), file); // cabeçalho

        while (fgets(line, sizeof(line), file)) {
            line[strcspn(line, "\n")] = '\0';
            Game g;
            initGame(&g);
            parseGame(&g, line);
            if (g.appId == idBusca) {
                printGame(&g);
                break;
            }
        }

        fclose(file);
    }

    return 0;
}
