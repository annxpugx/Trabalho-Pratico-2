#include <iostream>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sstream>
#include <time.h>
//using namespace std;
#define bool short
#define true 1
#define false 0
#define TAM_ARQUIVO 4000
#define TAM_LINHA 300

bool isFim(char *s)
{
    return (strlen(s) >= 3 && s[0] == 'F' &&
            s[1] == 'I' && s[2] == 'M');
}

char *replaceChar(char *string, char toSearch, char toReplace)
{
    char *charPtr = strchr(string, toSearch);
    if (charPtr != NULL)
        *charPtr = toReplace;
    return charPtr;
}

void lerLinha(char *string, int tamanhoMaximo, FILE *arquivo)
{
    fgets(string, tamanhoMaximo, arquivo);
    replaceChar(string, '\r', '\0');
    replaceChar(string, '\n', '\0');
}

char *strsep(char **stringp, const char *delim)
{
    char *begin, *end;
    begin = *stringp;

    if (begin == NULL)
        return NULL;

    /* Find the end of the token.  */
    end = begin + strcspn(begin, delim);

    if (*end)
    {
        /* Terminate the token and set *STRINGP past NUL character.  */
        *end++ = '\0';
        *stringp = end;
    }
    /* No more delimiters; this is the last token.  */
    else
        *stringp = NULL;

    return begin;
}

struct Jogador
{
    int id;
    int altura;
    int peso;
    int anoNascimento;
    char nome[100];
    char universidade[100];
    char cidadeNascimento[100];
    char estadoNascimento[100];

    //METODOS

    void salvar(char linha[TAM_LINHA])
    {
        char *substr[8];
        for (int i = 0; i < 8; i++)
            substr[i] = strsep(&linha, ",");

        int inteiros[4];
        sscanf(substr[2], "%d", &inteiros[0]);
        sscanf(substr[3], "%d", &inteiros[1]);
        sscanf(substr[5], "%d", &inteiros[2]);
        sscanf(substr[0], "%d", &inteiros[3]);

        altura = inteiros[0];
        peso = inteiros[1];
        anoNascimento = inteiros[2];
        id = inteiros[3];
        strcpy(nome, substr[1]);
        strcpy(universidade, substr[4]);
        strcpy(cidadeNascimento, substr[6]);
        strcpy(estadoNascimento, substr[7]);

        char corrige[] = "nao informado";
        if (strcmp(estadoNascimento, "") == 0)
            strcpy(estadoNascimento, corrige);
    }

    void imprimir()
    {
        printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n", id, nome, altura, peso, anoNascimento,
               universidade, cidadeNascimento, estadoNascimento);
    }

    void clone(Jogador J)
    {
        altura = J.altura;
        peso = J.peso;
        anoNascimento = J.anoNascimento;
        id = J.id;
        strcpy(nome, J.nome);
        strcpy(universidade, J.universidade);
        strcpy(cidadeNascimento, J.cidadeNascimento);
        strcpy(estadoNascimento, J.estadoNascimento);
    }
};

void ler(Jogador *Jogadores, int entradas[], int numEntrada)
{
    FILE *players;
    //abrindo o arquivo
    players = fopen("/tmp/players.csv", "r");

    char linhas[TAM_ARQUIVO][TAM_LINHA];
    char linhas_corrigidas[TAM_ARQUIVO][TAM_LINHA];
    int i = 0;
    char lixo[TAM_LINHA];
    lerLinha(lixo, TAM_LINHA, players);
    do
    {
        lerLinha(linhas[i++], TAM_LINHA, players);
    } while (!feof(players));
    i--;

    char corrige[] = ",nao informado";

    for (int j = 0; j < i; j++)
    {
        for (int z = 0, x = 0; x < strlen(linhas[j]); x++)
        {
            if (linhas[j][x] == ',' && linhas[j][x + 1] == ',')
            {
                strcat(linhas_corrigidas[j], corrige);
                z += 14;
            }
            else
            {
                linhas_corrigidas[j][z] = linhas[j][x];
                z++;
            }
        }
    }

    for (int i = 0; i < numEntrada; i++)
    {
        Jogadores[i].salvar(linhas_corrigidas[entradas[i]]);
        //cout << linhas_corrigidas[i] << endl;
    }
}

int getMax(Jogador arr[], int n, int *comp, int *mov) 
{ 
    Jogador mx = Jogador();
    mx.clone(arr[0]); 
    for (int i = 1; i < n; i++){
        (*comp)++;
        if (arr[i].id > mx.id) 
            mx = arr[i]; 
    }
    
    return mx.id; 
} 
  
// A function to do counting sort of arr[] according to 
// the digit represented by exp. 
void countSort(Jogador Jogadores[], int n, int exp, int *comp, int *mov) 
{ 
    Jogador output[1000]; // output array 
    int i, count[10] = {0}; 
  
    // Store count of occurrences in count[] 
    for (i = 0; i < n; i++) 
        count[(Jogadores[i].id / exp) % 10]++; 
  
    // Change count[i] so that count[i] now contains actual 
    //  position of this digit in output[] 
    for (i = 1; i < 10; i++) 
        count[i] += count[i - 1]; 
  
    // Build the output array 
    for (i = n - 1; i >= 0; i--) 
    { 
        output[count[(Jogadores[i].id / exp) % 10] - 1] = Jogador();
        output[count[(Jogadores[i].id / exp) % 10] - 1] = Jogadores[i]; 
        count[ (Jogadores[i].id / exp)%10 ]--; 
    } 
  
    // Copy the output array to arr[], so that arr[] now 
    // contains sorted numbers according to current digit 
    for(int i = 1; i < n; i++){
        Jogador tmp = Jogador();
        tmp.clone(output[i]);
        int j = i - 1;

        while (((j >= 0) && output[j].id == tmp.id && strcmp(output[j].nome, tmp.nome) > 0)) {
            (*comp)+=3;
            (*mov)++;
            output[j + 1].clone(output[j]);
            j--;
        }
        output[j + 1].clone(tmp);
        (*mov)++;
    }
    
    for(int i = 0; i < n; i++){
        Jogadores[i].clone(output[i]);
    }
} 
  
// The main function to that sorts arr[] of size n using  
// Radix Sort 
void radixsort(Jogador arr[], int n, int *comp, int *mov) 
{ 
    // Find the maximum number to know number of digits 
    int m = getMax(arr, n, comp, mov); 
  
    // Do counting sort for every digit. Note that instead 
    // of passing digit number, exp is passed. exp is 10^i 
    // where i is current digit number 
    for (int exp = 1; m/exp > 0; exp *= 10) 
        countSort(arr, n, exp, comp, mov); 
}

int main()
{
    char entrada_id[4000][10];
    int numEntrada_id = 0;
    do
    {
        lerLinha(entrada_id[numEntrada_id], 10, stdin);
    } while (isFim(entrada_id[numEntrada_id++]) == false);
    numEntrada_id--;

    int entrada_inteiro[4000];

    for (int i = 0; i < numEntrada_id; i++)
    {
        sscanf(entrada_id[i], "%d", &entrada_inteiro[i]);
    }

    char saida[4000][TAM_LINHA];
    Jogador _Jogadores[4000];

    ler(_Jogadores, entrada_inteiro, numEntrada_id);

    int comp = 0;
    int mov = 0;
    time_t tempo = clock();
    radixsort(_Jogadores, numEntrada_id, &comp, &mov);
    tempo = clock() - tempo;

    // printf("%s\n", entrada_nome[94]);
    for (int i = 0; i < numEntrada_id; i++)
    {
        _Jogadores[i].imprimir();
    }

    FILE *matricula;
    matricula = fopen("694370_radixsort.txt", "w");
    fprintf(matricula, "694370\t %d \t %d \t %lu", comp, mov, tempo);
    fclose(matricula);
}