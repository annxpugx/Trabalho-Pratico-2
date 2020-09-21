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
#define TAM_LINHA 200

bool isFim(char *s){
    return (strlen(s) >= 3 && s[0] == 'F' &&
        s[1] == 'I' && s[2] == 'M');
}

char *replaceChar(char *string, char toSearch, char toReplace)
{
    char *charPtr = strchr(string, toSearch);
    if (charPtr != NULL) *charPtr = toReplace;
    return charPtr;
}

void lerLinha(char *string, int tamanhoMaximo, FILE *arquivo)
{
    fgets(string, tamanhoMaximo, arquivo);
    replaceChar(string, '\r', '\0');
    replaceChar(string, '\n', '\0');
}

struct Jogador {
    int id;
    int altura;
    int peso;
    int anoNascimento;
    char nome[100];
    char universidade[100];
    char cidadeNascimento[100];
    char estadoNascimento[100];

    Jogador(){}
    Jogador(int id, char nome[], int altura, int peso, char universidade[], int anoNascimento , char cidadeNascimento[], char estadoNascimento[]){
        setId(id);
        setNome(nome);
        setAltura(altura);
        setPeso(peso);
        setAnoNascimento(anoNascimento);
        setUniversidade(universidade);
        setCidadeNacimento(cidadeNascimento);
        setEstadoNascimento(estadoNascimento);
    }
    //SETTERS
    void setId(int _id){
        id = _id;
    }

    void setAltura(int _altura){
        if(_altura > 1)
            altura = _altura;
    }

    void setPeso(int _peso){
        if(_peso > 0)
            peso = _peso;
    }

    void setAnoNascimento(int _anoNascimento){
        if(_anoNascimento > 1900)
            anoNascimento = _anoNascimento;
    }

    void setNome(char _nome[]){     
        strcpy(nome, _nome);         
    }

    void setUniversidade(char _universidade[]){
        strcpy(universidade,_universidade);
    }

    void setCidadeNacimento(char _cidadeNascimento[]){
        strcpy(cidadeNascimento, _cidadeNascimento);
    }

    void setEstadoNascimento(char _estadoNascimento[]){
        strcpy(estadoNascimento,_estadoNascimento);
    }
    //GETTERS
    char *getNome(){
        return nome;
    }

    char *getUniversidade(){
        return universidade;
    }

    char *getCidadeNacimento(){
        return cidadeNascimento;
    }

    char *getEstadoNascimento(){
        return estadoNascimento;
    }

    int getId(){
        return id;
    }

    int getAltura(){
        return altura;
    }

    int getPeso(){
        return peso;
    }

    int getAnoNascimento(){
        return anoNascimento;
    }

    //METODOS

    void salvar(int id, char *linha){
        char *strsep(char **stringp, const char *delim);
        char *substr[8];
        for(int i = 0; i < 8; i++)
            substr[i] = strsep(&linha, ",");

        int inteiros[3];
        sscanf(substr[2], "%d", &inteiros[0]);
        sscanf(substr[3], "%d", &inteiros[1]);
        sscanf(substr[5], "%d", &inteiros[2]);

        //Jogador J = Jogador(id, substr[1], inteiros[0], inteiros[1], substr[4], inteiros[2], substr[6], substr[7]);

        this->setId(id);
        // char *asteriscPtr = strchr(substr[1], '*');
        // if (asteriscPtr != NULL) *asteriscPtr = '\000';
        this->setNome(substr[1]);
        this->setAltura(inteiros[0]);
        this->setPeso(inteiros[1]);
        this->setUniversidade(substr[4]);
        this->setAnoNascimento(inteiros[2]);
        this->setCidadeNacimento(substr[6]);
        this->setEstadoNascimento(substr[7]);

        // printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n", J.getId(), J.getNome(), J.getAltura(), J.getPeso(), J.getAnoNascimento(), 
        //                                                         J.getUniversidade(), J.getCidadeNacimento(), J.getEstadoNascimento());
    }

};

char *ler (int id){
    FILE *players;
    //abrindo o arquivo
    players = fopen("/tmp/players.csv", "r");

    char linha[TAM_LINHA];
    char *linha_corrigida = (char *) malloc(sizeof(char) * TAM_LINHA);
    int i = 0;
    char lixo[TAM_LINHA];

    for(int i = 0; i <= id; i++)
        lerLinha(lixo, TAM_LINHA, players);
    
    lerLinha(linha, TAM_LINHA, players);

    strcpy(linha_corrigida, linha);
    fclose(players);
    
    return linha_corrigida;
}

bool pesqBin(char vet[][100], char x[], int tam, int *comp){
    bool resp = false;
    int dir = (tam - 1), esq = 0, meio;

    while (esq <= dir){
        meio = (esq + dir) / 2;
        if(strcmp(x, vet[meio]) == 0){
            (*comp)++;
            resp = true;
            esq = dir + 1;
        } else if (strcmp(x, vet[meio]) > 0) {
            (*comp)+= 2;
            esq = meio + 1;
        } else {
            (*comp) += 2;
            dir = meio - 1;
        }
    }
    return resp;
}

void swap(char array[][100], int i, int j) {
   char temp[100];
   strcpy(temp, array[i]);
   strcpy(array[i], array[j]);
   strcpy(array[j], temp);
}

void bolha(char array[][100], int n){
    int i, j;
    for (i = (n - 1); i > 0; i--) {
      for (j = 0; j < i; j++) {
         if (strcmp(array[j], array[j + 1]) > 0) {
            swap(array, j, j + 1);
         }
      }
   }
}

int main(){
    char entrada_id[200][10];
    int numEntrada_id = 0;
    do{
        lerLinha(entrada_id[numEntrada_id], 10, stdin);
    }while (isFim(entrada_id[numEntrada_id++]) == false);
    numEntrada_id--;
    //printf("%d\n", numEntrada_id);

    // for(int i = 0, j = 0; j < numEntrada_id/2; i++, j+= 2)
    //     strcpy(entrada_id[i],entrada_id[j]);

    // for(int i = 0; i < numEntrada_id; i++)
    //     printf("%s\n", entrada_id[i]);

    int entrada_inteiro[200];

    for(int i = 0; i < 200; i++){
        sscanf(entrada_id[i], "%d", &entrada_inteiro[i]);
    }

    char saida[100][TAM_LINHA];

    for(int i = 0; i < numEntrada_id; i++){
        char *csv = ler(entrada_inteiro[i]);
        strcpy(saida[i], csv);
        free(csv);
    }

    char entrada_nome[200][100];
    int numEntrada_nome = 0;

    do{
        lerLinha(entrada_nome[numEntrada_nome], 100, stdin);
    }while (isFim(entrada_nome[numEntrada_nome++]) == false);
    numEntrada_nome--;


    Jogador _Jogadores[200];
    char nomes[200][100];

    for(int i = 0; i < numEntrada_id; i++){
        _Jogadores[i] = Jogador();
        _Jogadores[i].salvar(entrada_inteiro[i], saida[i]);
        strcpy(nomes[i], _Jogadores[i].getNome());
    }

    // for(int i = 0; i < numEntrada_id; i++){
    //     printf("%s\n", nomes[i]);
    // }

    bolha(nomes, numEntrada_id);

    // for(int i = 0; i < numEntrada_id; i++){
    //     printf("%s\n", nomes[i]);
    // }

    time_t tempo = clock();
    int comp = 0;
    //printf("%s\n", entrada_nome[94]);
    for(int i = 0; i < numEntrada_nome; i++){
        if(pesqBin(nomes, entrada_nome[i], numEntrada_id, &comp) == true)
            printf("SIM\n");
        else
            printf("NAO\n");
    }

    tempo = clock() - tempo;

    FILE *matricula;
    matricula = fopen("matricula_binaria.txt", "w");
    fprintf(matricula, "694370\t %d \t %lu", comp, tempo);
    fclose(matricula);  
}