#include <stdio.h>
#include <stlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;

void tailPush(ElementoDiLista** l, int v) {
  if(l == NULL) {
    ElementoDiLista* head = malloc(sizeof(ElementoDiLista));
    head->info = v;
    head->next = *l;
    *l = head;
  } else if(l->next != NULL) {
    tailPush(&((*l)->next));
  } else {
    
  }
}

int main(int argc, char const *argv[]) {
  ElementoDiLista* lis = NULL;
  int exit = 0;
  int val;
  while(!exit) {
    scanf("%d", &val);
    if(val >= 0) {
      tailPush(&lis, val);
    } else {
      exit = 1;
    }
  }
  return 0;
}
