#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_LENGTH 100

typedef struct
{
    int id;
    char nome[MAX_LENGTH];
    int altura;
    int peso;
    char universidade[MAX_LENGTH];
    int anoNascimento;
    char cidadeNascimento[MAX_LENGTH];
    char estadoNascimento[MAX_LENGTH];
} Jogador;

void imprimir(Jogador *jogador)
{
    printf("[%i ## %s ## %i ## %i ## %i ## %s ## %s ## %s]\n", jogador->id, jogador->nome, jogador->altura, jogador->peso, jogador->anoNascimento, jogador->universidade, jogador->cidadeNascimento, jogador->estadoNascimento);
}

void replaceVirgula(char *str)
{
    int tamanho = strlen(str);
    char tmp[3 * tamanho];
    int j = 0;

    for (int i = 0; i < tamanho; i++)
    {
        if (str[i] == ',' && str[i + 1] == ',')
        {
            tmp[j++] = ',';
            tmp[j++] = 'n';
            tmp[j++] = 'a';
            tmp[j++] = 'o';
            tmp[j++] = ' ';
            tmp[j++] = 'i';
            tmp[j++] = 'n';
            tmp[j++] = 'f';
            tmp[j++] = 'o';
            tmp[j++] = 'r';
            tmp[j++] = 'm';
            tmp[j++] = 'a';
            tmp[j++] = 'd';
            tmp[j++] = 'o';
            tmp[j++] = ',';

            i++;
        }
        else
        {
            tmp[j++] = str[i];
        }
    }

    if (tmp[j - 2] == ',')
    {
        tmp[j - 1] = 'n';
        tmp[j++] = 'a';
        tmp[j++] = 'o';
        tmp[j++] = ' ';
        tmp[j++] = 'i';
        tmp[j++] = 'n';
        tmp[j++] = 'f';
        tmp[j++] = 'o';
        tmp[j++] = 'r';
        tmp[j++] = 'm';
        tmp[j++] = 'a';
        tmp[j++] = 'd';
        tmp[j++] = 'o';
    }

    tmp[j] = '\0';
    strcpy(str, tmp);
}

void clone(Jogador *jogador, Jogador *novo)
{
    novo->id = jogador->id;
    strcpy(novo->nome, jogador->nome);
    novo->altura = jogador->altura;
    novo->peso = jogador->peso;
    strcpy(novo->universidade, jogador->universidade);
    novo->anoNascimento = jogador->anoNascimento;
    strcpy(novo->cidadeNascimento, jogador->cidadeNascimento);
    strcpy(novo->estadoNascimento, jogador->estadoNascimento);
}

void ler(Jogador *jogador, char str[300])
{
    replaceVirgula(str);
    str[strcspn(str, "\n")] = '\0';

    char *token;
    token = strtok(str, ",");
    int i = 0;

    while (token != NULL)
    {
        if (i % 8 == 0)
        {
            jogador->id = atoi(token);
        }
        else if (i % 8 == 1)
        {
            strcpy(jogador->nome, token);
        }
        else if (i % 8 == 2)
        {
            jogador->altura = atoi(token);
        }
        else if (i % 8 == 3)
        {
            jogador->peso = atoi(token);
        }
        else if (i % 8 == 4)
        {
            strcpy(jogador->universidade, token);
        }
        else if (i % 8 == 5)
        {
            jogador->anoNascimento = atoi(token);
        }
        else if (i % 8 == 6)
        {
            strcpy(jogador->cidadeNascimento, token);
        }
        else if (i % 8 == 7)
        {
            strcpy(jogador->estadoNascimento, token);
        }
        i++;

        token = strtok(NULL, ",");
    }
}

void swap(Jogador array[], int i, int j)
{
    Jogador temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}

Jogador getMaior(Jogador *array, int n)
{
    Jogador maior = array[0];

    for (int i = 1; i < n; i++)
    {
        if (maior.id < array[i].id)
        {
            maior = array[i];
        }
    }
    return maior;
}

void radcountingSort(Jogador *array, int n, int exp, int *m, int *c)
{
    int count[10];
    Jogador output[n];

    for (int i = 0; i < 10; count[i] = 0, i++)
        ;
    for (int i = 0; i < n; i++)
    {
        count[(array[i].id / exp) % 10]++;
    }
    for (int i = 1; i < 10; i++)
    {
        count[i] += count[i - 1];
    }
    for (int i = n - 1; i >= 0; i--)
    {
        output[count[(array[i].id / exp) % 10] - 1] = array[i];
        (*m)++;
        count[(array[i].id / exp) % 10]--;
    }
    for (int i = 0; i < n; i++)
    {
        array[i] = output[i];
    }
}
void radixsort(Jogador *array, int n, int *m, int *c)
{
    Jogador max = getMaior(array, n);
    for (int exp = 1; max.id / exp > 0; exp *= 10)
    {
        radcountingSort(array, n, exp, m, c);
    }
}

int main()
{

    Jogador players[3923];
    Jogador clonedPlayers[3923];
    int contador = 0;
    char n[5];

    FILE *arq = fopen("/tmp/players.csv", "r");

    if (arq == NULL)
    {
        printf("File not found\n");
        return 0;
    }

    char str[300];
    fgets(str, sizeof(str), arq);
    int i = 0;
    while (fgets(str, sizeof(str), arq))
    {
        ler(&players[i], str);
        i++;
    }

    scanf("%s", n);
    int j = 0;
    while (strcmp(n, "FIM") != 0)
    {
        int indice = atoi(n);
        clone(&players[indice], &clonedPlayers[contador++]);
        j++;
        scanf("%s", n);
    }

    char name[100];
    scanf(" %[^\n]s", name);

    FILE *tempArq = fopen("1445217_radixsort.txt", "w");
    clock_t inicio, fim;
    double total;

    inicio = clock();
    int mov = 0, comp = 0;
    radixsort(clonedPlayers, j, &mov, &comp);
    fim = clock();

    total = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    fprintf(tempArq, "1445217\t%fs.\t%d", total, comp);
    fclose(tempArq);

    for (int i = 0; i < j; i++)
    {
        imprimir(&clonedPlayers[i]);
    }

    fclose(arq);
    return 0;
}