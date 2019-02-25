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
  ElementoDiLista* head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = *l;
  *l = head;
}

int even(int v) {
  return !(v%2);
}

void addBeforeEven(ElementoDiLista** l) {
  ElementoDiLista* aux = *l;
  if(aux != NULL) {
    if(even(aux->info)) {
      ElementoDiLista* el = malloc(sizeof(ElementoDiLista));
      el->info = -1;
      el->next = aux;
      *l = el;
      addBeforeEven(&((*l)->next->next));
    } else {
      addBeforeEven(&((*l)->next));
    }
  }
}

int main(int argc, char const *argv[]) {
  int exit = 0;
  int tmpVal;
  ElementoDiLista* list = NULL;
  while(!exit) {
    scanf("%d", &tmpVal);
    if(tmpVal > 0) {
      push(&list, tmpVal);
    } else {
      exit = 1;
    }
  }
  addBeforeEven(&list);
  RecStampa(list);
  return 0;
}
