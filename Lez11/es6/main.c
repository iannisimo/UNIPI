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

void pushOrd(ElementoDiLista** l, int v) {
  if(*l != NULL) {
    if((*l)->info <= v) {
      pushOrd(&((*l)->next), v);
    } else {
      push(l, v);
    }
  } else {
    push(l, v);
  }
}

int main(int argc, char const *argv[]) {
  ElementoDiLista* l = NULL;
  int in;
  int stop = 0;
  while(!stop) {
    scanf("%d", &in);
    if(in >= 0) {
      pushOrd(&l, in);
    } else {
      stop = 1;
    }
  }
  RecStampa(l);
  return 0;
}
