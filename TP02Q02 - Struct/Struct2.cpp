#include <iostream>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sstream>
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

    Jogador(int id){
        setId(id);
    }
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

};

void imprimir(int id, char linha[TAM_LINHA]){
    char *strsep(char **stringp, const char *delim);
    char *substr[8];
    for(int i = 0; i < 8; i++)
        substr[i] = strsep(&linha, ",");

    int inteiros[3];
    sscanf(substr[2], "%d", &inteiros[0]);
    sscanf(substr[3], "%d", &inteiros[1]);
    sscanf(substr[5], "%d", &inteiros[2]);

    Jogador J = Jogador(id, substr[1], inteiros[0], inteiros[1], substr[4], inteiros[2], substr[6], substr[7]);

    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n", J.getId(), J.getNome(), J.getAltura(), J.getPeso(), J.getAnoNascimento(), 
                                                            J.getUniversidade(), J.getCidadeNacimento(), J.getEstadoNascimento());
}

void ler (int entradas[], int numEntrada){
    FILE *players;
    //abrindo o arquivo
    players = fopen("/tmp/players.csv", "rw");

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
        imprimir(entradas[i], linhas_corrigidas[entradas[i]]);
        //cout << linhas_corrigidas[i] << endl;
    }
}

int main(){
    char entrada[100][5];
    int numEntrada = 0;
    int linha = 0;
    char lixo[5];
    do{
        lerLinha(entrada[numEntrada], 1000, stdin);
    }while (isFim(entrada[numEntrada++]) == false);
    numEntrada--;

    int entrada_inteiro[100];

    for(int i = 0; i < 100; i++){
        sscanf(entrada[i], "%d", &entrada_inteiro[i]);
    }

    ler(entrada_inteiro, numEntrada);
}