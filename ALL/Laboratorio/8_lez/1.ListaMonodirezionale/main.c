#include <stdlib.h>
#include <stdio.h>

typedef struct _lstEl {
  int val;
  struct _lstEl* next;
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
      (*l)->val = v;
      (*l)->next = NULL;
      ptr = *l;
    } else {
      ptr->next = malloc(sizeof(lstEl));
      ptr->next->val = v;
      ptr->next->next = NULL;
      ptr = ptr->next;
    }
  }
}

void printReverse(lstEl* l) {
  if(l != NULL) {
    printReverse(l->next);
    printf("%d\n", l->val);
  }
}
