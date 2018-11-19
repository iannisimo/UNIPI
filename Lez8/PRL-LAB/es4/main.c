#include <stdio.h>
#include <stdlib.h>

#define debug printf("Debug\n");

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

int memberList(ElementoDiLista* l, int v) {
  int found = 0;
  while(!found && l != NULL) {
    if(l->info == v) {
      found = 1;
    } else {
      l = l->next;
    }
  }
  return found;
}

void push(ElementoDiLista** l, int v) {
  ElementoDiLista* head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = *l;
  *l = head;
}

int main(int argc, char const *argv[]) {
  ElementoDiLista* l[2] = {NULL, NULL};
  ElementoDiLista* out = NULL;
  int exit;
  int i;
  int tmpVal = 0;
  for(i = 0; i < 2; i++) {
    exit = 0;
    while(!exit) {
      scanf("%d", &tmpVal);
      if(tmpVal >= 0) {
        push(&l[i], tmpVal);
      } else {
        exit = 1;
      }
    }
  }
  if(l[0] != NULL && l[1] != NULL) {
    if(l[0]->info < l[1]->info) {
      ElementoDiLista* tmp = l[0];
      l[0] = l[1];
      l[1] = tmp;
    }
    while(l[0] != NULL) {
      if(memberList(l[1], l[0]->info) && !memberList(out, l[0]->info)) {
        push(&out, l[0]->info);
      }
      l[0] = l[0]->next;
    }
    printAll(out);
  }
  return 0;
}
