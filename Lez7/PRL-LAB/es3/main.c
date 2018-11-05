#include <stdio.h>
#include <stdlib.h>

struct persona {
  int nome[10];
  struct persona* mamma;
  struct persona* babbo;
  };
typedef struct persona Persona;

void printAnchestors(Persona* tmp) {
  int i;
  int mum = 0;
  int dad = 0;
  for(i = 0; i < 10; i++) {
    printf("%c", tmp->nome[i]);
  }
  printf("\n");
  if(tmp->mamma != NULL) {
    mum = 1;
    for(i = 0; i < 10; i++) {
      printf("%c", tmp->mamma->nome[i]);
    }
  } else {
    printf("Sconosciuto");
  }
  if(tmp->babbo != NULL) {
    dad = 1;
    for(i = 0; i < 10; i++) {
      printf("%c", tmp->babbo->nome[i]);
    }
    printf("\n");
  } else {
    printf("Sconosciuto\n");
  }
  if(mum) {
    printAnchestors(tmp->mamma);
  }
  if(dad) {
    printAnchestors(tmp->babbo);
  }
}

int main(int argc, char const *argv[]) {
  int i;

  Persona* figlio = malloc(sizeof(Persona));
  figlio->mamma = malloc(sizeof(Persona));
  figlio->babbo = malloc(sizeof(Persona));
  figlio->mamma->mamma = NULL;
  figlio->mamma->babbo = NULL;
  figlio->babbo->mamma = NULL;
  figlio->babbo->babbo = NULL;
  for(i = 0; i < 10; i++) {
    scanf("%c", &(figlio->nome[i]));
  }
  for(i = 0; i < 10; i++) {
    scanf("%c", &(figlio->mamma->nome[i]));
  }
  for(i = 0; i < 10; i++) {
    scanf("%c", &(figlio->babbo->nome[i]));
  }
  printAnchestors(figlio);
  free(figlio->mamma);
  free(figlio->babbo);
  free(figlio);
  return 0;
}
