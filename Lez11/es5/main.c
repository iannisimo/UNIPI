#include <stdio.h>
#include <stdlib.h>

#define STEPS 4

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
  int out = 1;
  if(l->info < m) {
    if(l->info > n) {
      out = l->info;
    }
    int next = prodInterval(l->next, n, m);
    if(next < m) {
      out *= next;
    }
  } else {
    out = l->info;
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
  RecStampa(l);
  printf("%d\n", prod);
  return 0;
}
