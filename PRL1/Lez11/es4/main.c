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

void tailPush(ElementoDiLista** l, int v) {
  if(*l == NULL) {
    push(l, v);
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

void AddAfterStepsAux(ElementoDiLista** l, int val, int n) {
  if(*l != NULL && n > 0) {
    AddAfterStepsAux(&((*l)->next), val, n-1);
  } else {
    push(l, val);
  }
}

void addAfterSteps(ElementoDiLista** l, int val) {
  AddAfterStepsAux(l, val, STEPS);
}

int main(int argc, char const *argv[]) {
  ElementoDiLista* l = NULL;
  int in;
  int stop = 0;
  while(!stop) {
    scanf("%d", &in);
    if(in >= 0) {
      tailPush(&l, in);
    } else {
      stop = 1;
    }
  }
  scanf("%d", &in);
  addAfterSteps(&l, in);
  RecStampa(l);
  return 0;
}
