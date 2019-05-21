#include <stdlib.h>
#include <stdio.h>

typedef struct _lstEl {
  int v;
  struct _lstEl* n;
  struct _lstEl* p;
} lstEl;

void readList(lstEl**);
void printReverse(lstEl*);

int main(int argc, char const *argv[]) {
  lstEl* list = NULL;
  readList(&list);
  printReverse(list);
  return 0;
}

void readList(lstEl** l) {
  int n;
  scanf("%d", &n);
  lstEl* ptr;
  for(int i = 0; i < n; i++) {
    int v;
    scanf("%d", &v);
    if(*l == NULL) {
      (*l) = malloc(sizeof(lstEl));
      (*l)->v = v;
      (*l)->n = NULL;
      (*l)->p = NULL;
      ptr = *l;
    } else {
      ptr->n = malloc(sizeof(lstEl));
      ptr->n->v = v;
      ptr->n->n = NULL;
      ptr->n->p = ptr;
      ptr = ptr->n;
    }
  }
}

void printReverse(lstEl* l) {
  while(l->n != NULL) {
    l = l->n;
  }
  while(l != NULL) {
    printf("%d\n", l->v);
    l = l->p;
  }
}
