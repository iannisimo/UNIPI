#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;

void RecStampaInversa(ElementoDiLista* el, ElementoDiLista* head) {
  if(el != NULL) {
    int val = el->info;
    RecStampaInversa(el->next, head);
    printf("%d -> ", val);
  }
  if(el == head) {
    printf("NULL\n");
  }
}

void push(ElementoDiLista** l, int v) {
  ElementoDiLista* head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = *l;
  *l = head;
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
  RecStampaInversa(list, list);
  return 0;
}
