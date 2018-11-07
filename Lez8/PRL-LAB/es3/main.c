#include <stdio.h>
#include <stdlib.h>

#define VERY_SMALL -1

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
  if(head != NULL) {
    ListaDiElementi lPointer;
    ListaDiElementi el = malloc(sizeof(ElementoDiLista));
    lPointer->info = VERY_SMALL;
    lPointer->next = head;
    ListaDiElementi out = lPointer;
    el->info = v;
    while(lPointer->next != NULL && lPointer->next->info < v) {
      lPointer = lPointer->next;
    }
    ListaDiElementi tmp;
    tmp = lPointer->next;
    lPointer->next = el;
    el->next = tmp;
  } else {
    push(head, v);
  }
  return out->next;
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
      printf(">\n");
      head = pushOrdered(head, tmpVal);
      printAll(head);
      printf(">\n");
    }
  }
  return 0;
}
