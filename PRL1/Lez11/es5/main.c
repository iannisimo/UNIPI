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

void pushOrd(ElementoDiLista** l, int v) {
  if(*l != NULL) {
    if((*l)->info != v) {
      if((*l)->info < v) {
        pushOrd(&((*l)->next), v);
      } else {
        push(l, v);
      }
    }
  } else {
    push(l, v);
  }
}

int prodInterval(ElementoDiLista* l, int n, int m) {
  int stop = 0;
  int out = -1;
  while(l != NULL && !stop) {
    if(l->info < m) {
      if(out == -1) out = 1;
      if(l->info > n) {
        out *= l->info;
      }
      l = l->next;
    } else {
      stop = 1;
    }
  }
  return out;
}

int main(int argc, char const *argv[]) {
  ElementoDiLista* l = NULL;
  int N;
  int M;
  int in;
  int stop = 0;
  scanf("%d %d", &N, &M);
  while(!stop) {
    scanf("%d", &in);
    if(in >= 0) {
      pushOrd(&l, in);
    } else {
      stop = 1;
    }
  }
  int prod = prodInterval(l, N, M);
  printf("%d\n", prod);
  return 0;
}
