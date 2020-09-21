#include <iostream>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sstream>
#include <time.h>
#include <math.h>
using namespace std;
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

    void imprimir(){
        printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n", this->getId(), this->getNome(), this->getAltura(), this->getPeso(), this->getAnoNascimento(), 
                                                                this->getUniversidade(), this->getCidadeNacimento(), this->getEstadoNascimento());
    }

    char *strsep(char **stringp, const char *delim)
{
    char *begin, *end;
    begin = *stringp;

    if (begin == NULL) return NULL;

    /* Find the end of the token.  */
    end = begin + strcspn(begin, delim);

    if (*end)
    {
        /* Terminate the token and set *STRINGP past NUL character.  */
        *end++ = '\0';
        *stringp = end;
    }
    /* No more delimiters; this is the last token.  */
    else *stringp = NULL;
    
    return begin;
}

    void salvar(int id, char linha[TAM_LINHA]){
        // char *strsep(char **stringp, const char *delim);
        char *substr[8];
        for(int i = 0; i < 8; i++)
            substr[i] = strsep(&linha, ",");

        int inteiros[3];
        sscanf(substr[2], "%d", &inteiros[0]);
        sscanf(substr[3], "%d", &inteiros[1]);
        sscanf(substr[5], "%d", &inteiros[2]);

        this->setId(atoi(substr[0]));
        // char *asteriscPtr = strchr(substr[1], '*');
        // if (asteriscPtr != NULL) *asteriscPtr = '\000';
        this->setNome(substr[1]);
        this->setAltura(inteiros[0]);
        this->setPeso(inteiros[1]);
        this->setUniversidade(substr[4]);
        this->setAnoNascimento(inteiros[2]);
        this->setCidadeNacimento(substr[6]);
        this->setEstadoNascimento(substr[7]);;
    }

    void copiar(Jogador J){
        this->setId(J.getId());
        this->setNome(J.getNome());
        this->setAltura(J.getAltura());
        this->setPeso(J.getPeso());
        this->setUniversidade(J.getUniversidade());
        this->setAnoNascimento(J.getAnoNascimento());
        this->setCidadeNacimento(J.getCidadeNacimento());
        this->setEstadoNascimento(J.getEstadoNascimento());;
    }

};

void swap(Jogador *i, Jogador *j) {
   Jogador temp = *i;
   *i = *j;
   *j = temp;
}

void SelectionSortR(Jogador *Jogadores, int i, int n){
    Jogador temp = Jogador();

    if (i < n - 1) {
        int menor = i;
        for (int j = (i + 1); j < n; j++){
            if (strcmp(Jogadores[menor].getNome(), Jogadores[j].getNome()) > 0){
                menor = j;
            }
        }
        swap(&Jogadores[menor], &Jogadores[i]);
        SelectionSortR(Jogadores, i+1, n);
    }
}

void ler (Jogador *Jogadores, int entradas[], int numEntrada){
    FILE *players;
    //abrindo o arquivo
    players = fopen("players.csv", "r");

    char linhas[TAM_ARQUIVO][TAM_LINHA];
    char linhas_corrigidas[TAM_ARQUIVO][TAM_LINHA];
    int i = 0;
    char lixo[TAM_LINHA];
    lerLinha(lixo, TAM_LINHA, players);
    do{
        lerLinha(linhas[i++], TAM_LINHA, players);
    } while(!feof(players));
    i--;

    char corrige[] = ",nao informado";

    for(int j = 0; j < i; j++){
        for(int z = 0, x = 0; x < strlen(linhas[j]); x++){
            if(linhas[j][x] == ',' && linhas[j][x+1] == ','){
                strcat(linhas_corrigidas[j],corrige);
                z += 14;
            }else{
                linhas_corrigidas[j][z] = linhas[j][x];
                z++;
            }
            if(x == strlen(linhas[j]) - 2 && linhas[j][x] == ',')
                strcat(linhas_corrigidas[j],corrige);
        }
    }

    for(int i = 0; i < numEntrada; i++){
        Jogadores[i].salvar(entradas[i], linhas_corrigidas[entradas[i]]);
        //cout << linhas_corrigidas[i] << endl;
    }
}

int main(){
    char entrada[1000][10];
    int numEntrada = 0;
    int linha = 0;
    do{
        lerLinha(entrada[numEntrada], 10, stdin);
    }while (isFim(entrada[numEntrada++]) == false);
    numEntrada--;

    int entrada_inteiro[1000];

    for(int i = 0; i < 1000; i++){
        sscanf(entrada[i], "%d", &entrada_inteiro[i]);
    }

    Jogador _Jogadores[1000];

    ler(_Jogadores, entrada_inteiro, numEntrada);

    // for(int i = 0; i < numEntrada; i++){
    //     _Jogadores[i].imprimir();
    // }
    time_t tempo = clock();
    SelectionSortR(_Jogadores, 0, numEntrada);
    tempo = clock() - tempo;
    for(int i = 0; i < numEntrada; i++){
        _Jogadores[i].imprimir();
    }

    FILE *matricula;
    matricula = fopen("matricula_selecaoRecursiva.txt", "w");
    int comp = pow(numEntrada, 2)/2 - numEntrada/2;
    int mov = 3*(numEntrada - 1);
    fprintf(matricula, "694370\t %d \t %d \t %lu", comp, mov, tempo);
    fclose(matricula);  
}