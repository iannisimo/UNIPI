#include <stdio.h>
#include <stdlib.h>

typedef struct _btree{
  int v;
  struct _btree *l;
  struct _btree *r;
} BTree;

BTree* readBTree(int*);
void printBTree(BTree*);

int main(int argc, char const *argv[]) {
  int dim;
  BTree* radix = readBTree(&dim);
  printBTree(radix);
  return 0;
}

BTree* readBTree(int* dim) {
  BTree* radix = NULL;
  scanf("%d", dim);
  int in;
  for(int i = 0; i < *dim; i++) {
    scanf("%d", &in);
    BTree* prec = NULL;
    BTree* ptr = radix;
    if(ptr == NULL) {
      radix = malloc(sizeof(BTree));
      radix->v = in;
      radix->l = NULL;
      radix->r = NULL;
    } else {
      while(ptr != NULL) {
        prec = ptr;
        if(in < ptr->v) {
          ptr = ptr->l;
        } else {
          ptr = ptr->r;
        }
      }
      if(in < prec->v) {
        prec->l = malloc(sizeof(BTree));
        prec->l->v = in;
        prec->l->l = NULL;
        prec->l->r = NULL;
      } else {
        prec->r = malloc(sizeof(BTree));
        prec->r->v = in;
        prec->r->l = NULL;
        prec->r->r = NULL;
      }
    }
  }
  return radix;
}

void printBTree(BTree* radix) {
  if(radix == NULL) return;
  printBTree(radix->l);
  printf("%d\n", radix->v);
  printBTree(radix->r);
}
