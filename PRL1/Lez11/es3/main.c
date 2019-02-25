#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;

void RecStampa(ElementoDiLista* el) {
  if(el != NULL) {
    printf("%d -> ", el->info);
    RecStampa(el->next);
  } else {
    printf("NULL\n");
  }
}

void push(ElementoDiLista** l, int v) {
  if(*l == NULL) {
    *l = malloc(sizeof(ElementoDiLista));
    (*l)->info = v;
    (*l)->next = NULL;
  } else {
    ElementoDiLista* lPointer = *l;
    while(lPointer->next != NULL) {
      lPointer = lPointer->next;
    }
    lPointer->next = malloc(sizeof(ElementoDiLista));
    lPointer->next->info = v;
    lPointer->next->next = NULL;
  }
}

void delFirst(int n, ElementoDiLista** l) {
  if(*l != NULL) {
    if(n > 0) {
      ElementoDiLista* aux = *l;
      *l = aux->next;
      free(aux);
      delFirst(n-1, l);
    }
  }
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
  scanf("%d", &in);
  delFirst(in, &l);
  RecStampa(l);
  return 0;
}
