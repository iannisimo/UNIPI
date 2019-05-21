#include <stdlib.h>
#include <stdio.h>

typedef struct _lstEl {
  int v;
  int c;
  struct _lstEl* n;
  struct _lstEl* p;
} lstEl;

void readList(lstEl**);
int findAndOrder(lstEl**, int);

int main(int argc, char const *argv[]) {
  lstEl* list = NULL;
  readList(&list);
  int inpt;
  int otpt = 0;
  do {
    scanf("%d", &inpt);
    otpt = findAndOrder(&list, inpt);
    printf("%d\n", otpt);
  } while (otpt+1);
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

// --- -a- -b- ---
// --- -b- -a- ---

void swap(lstEl** b, lstEl** a) {
  /*
  lstEl* aa = *a;
  lstEl* bb = *b;
  aa->n = (*b)->n;
  aa->p = *b;
  bb->n = *a;
  bb->p = (*a)->p;
  *a = bb;
  *b = aa;
  */

  // TODO: Better swap

  lstEl* tmp = malloc(sizeof(lstEl));
  tmp->v = (*a)->v;
  tmp->c = (*a)->c;
  (*a)->v = (*b)->v;
  (*a)->c = (*b)->c;
  (*b)->v = tmp->v;
  (*b)->c = tmp->c;
  free(tmp);
}

int findAndOrder(lstEl** l, int val) {
  lstEl* ptr = *l;
  int index = 0;
  while (ptr != NULL) {
    if(ptr->v == val) {
      ptr->c++;
      while(ptr->p != NULL && ptr->c > ptr->p->c) {
        swap(&ptr, &(ptr->p));
        ptr = ptr->p;
      }
      return index;
    }
    index++;
    ptr = ptr->n;
  }
  return -1;
}
