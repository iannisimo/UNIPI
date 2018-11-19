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

void RecStampa(ElementoDiLista* el) {
  if(el != NULL) {
    printf("%d -> ", el->info);
    RecStampa(el->next);
  } else {
    printf("NULL\n");
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
      RecStampa(l);
    }
  }
  return 0;
}
