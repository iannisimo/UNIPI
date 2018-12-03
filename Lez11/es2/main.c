#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;

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

void findEvenOdd(ElementoDiLista* l, int* even, int* odd) {
  if(l != NULL) {
    if(l->info%2 && *odd == -1) {
      *odd = l->info;
    } else if (!(l->info%2) && *even == -1) {
      *even = l->info;
    }
    findEvenOdd(l->next, even, odd);
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
  int even = -1;
  int odd = -1;
  findEvenOdd(l, &even, &odd);
  printf("%d\n%d\n", odd, even);
  return 0;
}
