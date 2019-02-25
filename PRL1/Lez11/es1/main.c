#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;

void push(ElementoDiLista** l, int v) {
  ElementoDiLista* head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = *l;
  *l = head;
}

int CalcoloSomma(ElementoDiLista* l) {
  int out = 0;
  if(l != NULL) {
    out = l->info + CalcoloSomma(l->next);
  }
  return out;
}

int CalcoloNElementi(int somma, ElementoDiLista* l) {
  int out = 0;
  if(l != NULL) {
    if((l->info) > (somma/4)) {
      out = 1;
    }
    out += CalcoloNElementi(somma, l->next);
  }
  return out;
}

int main(int argc, char const *argv[]) {
  ElementoDiLista* l = NULL;
  int in;
  int stop = 0;
  while(!stop) {
    scanf("%d", &in);
    if(in >= 0) {
      push(&l, in);
    } else {
      stop = 1;
    }
  }
  int somma = CalcoloSomma(l);
  int nElementi = CalcoloNElementi(somma, l);
  printf("%d\n%d\n", somma, nElementi);
  return 0;
}
