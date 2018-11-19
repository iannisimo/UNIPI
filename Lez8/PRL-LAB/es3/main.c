#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;
typedef ElementoDiLista* ListaDiElementi;

void printAll(ListaDiElementi head) {
  if(head != NULL) {
    printf("%d\n", head->info);
    printAll(head->next);
  }
}

ListaDiElementi push(ListaDiElementi oldHead, int v) {
  ListaDiElementi head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = oldHead;
  return head;
}
ListaDiElementi pushOrdered(ListaDiElementi head, int v) {
  ListaDiElementi out = head;
  if(head != NULL  && head->info < v) {
    ListaDiElementi lPointer = head;
    while(lPointer->next != NULL && lPointer->next->info < v) {
      lPointer = lPointer->next;
    }
    ListaDiElementi el = malloc(sizeof(ElementoDiLista));
    el->next = lPointer->next;
    lPointer->next = el;
    el->info = v;
  } else {
    out = push(head, v);
  }
  return out;
}

int main(int argc, char const *argv[]) {
  int exit = 0;
  int tmpVal;
  ListaDiElementi head = NULL;
  while(!exit) {
    scanf("%d", &tmpVal);
    if(tmpVal < 0) {
      printAll(head);
      exit = 1;
    }
    else {
      head = pushOrdered(head, tmpVal);
    }
  }
  return 0;
}
