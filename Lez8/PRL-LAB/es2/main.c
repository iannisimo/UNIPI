#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;

void printAll(ElementoDiLista* head) {
  ElementoDiLista* tmpHead = head;
  if(tmpHead != NULL) {
    printf("%d\n", tmpHead->info);
    printAll(tmpHead->next);
  }
}

void headPush(ElementoDiLista** l, int v) {
  ElementoDiLista* head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = *l;
  *l = head;
}

void tailPush(ElementoDiLista** l, int v) {
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

void removeVal(ElementoDiLista** l, int v) {
  ElementoDiLista* prec = NULL;
  ElementoDiLista* curr = *l;
  int exit = 0;
  while(curr != NULL) {
    if(curr->info == v) {
      if(prec == NULL) {
        *l = curr->next;
        free(curr);
        curr = *l;
      } else {
        prec->next = curr->next;
        free(curr);
        curr = prec->next;
      }
    } else {
      prec = curr;
      curr = curr->next;
    }
  }
}

int main(int argc, char const *argv[]) {
  int exit = 0;
  int tmpVal;
  ElementoDiLista* head = NULL;
  while(!exit) {
    scanf("%d", &tmpVal);
    if(tmpVal < 0) {
      removeVal(&head, (-1)*tmpVal);
    } else if(tmpVal > 0) {
      if(tmpVal%2) {
        tailPush(&head, tmpVal);
      } else {
        headPush(&head, tmpVal);
      }
    } else {
      printAll(head);
      exit = 1;
    }
  }
  return 0;
}
