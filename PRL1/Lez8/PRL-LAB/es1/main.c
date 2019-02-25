#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;
typedef ElementoDiLista* ListaDiElementi;

ListaDiElementi push(ListaDiElementi oldHead, int v) {
  ListaDiElementi head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = oldHead;
  return head;
}

ListaDiElementi pop(ListaDiElementi oldHead) {
  ListaDiElementi head = oldHead;
  if(head != NULL) {
    head = oldHead->next;
    free(oldHead);
  }
  return head;
}

void printAll(ListaDiElementi head) {
  if(head != NULL) {
    printf("%d\n", head->info);
    printAll(head->next);
  }
}

int main(int argc, char const *argv[]) {
  int stop = 0;
  int tmpVal;
  ListaDiElementi head = NULL;
  while(!stop) {
    scanf("%d", &tmpVal);
    if(tmpVal > 0) {
      head = push(head, tmpVal);
    } else if(tmpVal == 0) {
      head = pop(head);
    } else {
      printAll(head);
      stop = 1;
    }
  }
  return 0;
}
