#include <stdlib.h>
#include <stdio.h>

typedef struct _lstEl {
  int v;
  struct _lstEl* n;
} lstEl;

void readList(lstEl**);
int mtf(lstEl**, int);

int main(int argc, char const *argv[]) {
  lstEl* list = NULL;
  readList(&list);
  int inpt;
  int otpt = 0;
  while(otpt+1) {
    scanf("%d", &inpt);
    otpt = mtf(&list, inpt);
    printf("%d\n", otpt);
  }
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
      ptr = *l;
    } else {
      ptr->n = malloc(sizeof(lstEl));
      ptr->n->v = v;
      ptr->n->n = NULL;
      ptr = ptr->n;
    }
  }
}

int mtf(lstEl** head, int val) {
  lstEl* prec = NULL;
  lstEl* ptr = *head;
  int index = 0;
  while(ptr != NULL) {
    if(ptr->v == val) {
      if(prec != NULL) {
        prec->n = ptr->n;
        ptr->n = *head;
        *head = ptr;
      }
      return index;
    }
    index++;
    prec = ptr;
    ptr = ptr->n;
  }
  return -1;
}
