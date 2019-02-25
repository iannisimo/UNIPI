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

int RecLenght(ElementoDiLista* el) {
  int out = 0;
  if(el->next != NULL) {
    out = RecLenght(el->next) + 1;
  } else {
    out = 1;
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
      printf("%d\n", RecLenght(l));
    }
  }
  return 0;
}
