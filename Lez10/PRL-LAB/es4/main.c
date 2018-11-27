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

int multiple(int n, int m) {
  return !(n%m);
}

void push(ElementoDiLista** l, int v) {
  ElementoDiLista* head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = *l;
  *l = head;
}

void recRemove(ElementoDiLista** l, int n) {
  if(*l != NULL) {
    if(multiple((*l)->info, n)) {
      ElementoDiLista* aus = *l;
      *l = aus->next;
      free(aus);
      recRemove(l, n);
    } else {
      recRemove(&((*l)->next), n);
    }
  }
}

int main(int argc, char const *argv[]) {
  int exit = 0;
  int tmpVal;
  int n;
  scanf("%d", &n);
  ElementoDiLista* list = NULL;
  while(!exit) {
    scanf("%d", &tmpVal);
    if(tmpVal > 0) {
      push(&list, tmpVal);
    } else {
      exit = 1;
    }
  }
  recRemove(&list, n);
  RecStampa(list);
  return 0;
}
