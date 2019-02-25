#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;

typedef
  enum {false, true}
boolean;

void push(ElementoDiLista** l, int v) {
  ElementoDiLista* head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = *l;
  *l = head;
}

boolean recRising(ElementoDiLista* el) {
  boolean out = true;
  if(el->next != NULL) {
    if(el->info > el->next->info) {
      out = recRising(el->next);
    } else {
      out = false;
    }
  } else {
    out = true;
  }
  return out;
}

int main(int argc, char const *argv[]) {
  int exit = 0;
  int tmpVal;
  ElementoDiLista* list = NULL;
  while(!exit) {
    scanf("%d", &tmpVal);
    if(tmpVal > 0) {
      push(&list, tmpVal);
    } else {
      exit = 1;
    }
  }
  boolean isRising = recRising(list);
  if(isRising == false) {
    printf("False\n");
  } else {
    printf("True\n");
  }
  return 0;
}
