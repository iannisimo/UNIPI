#include <stdio.h>
#include <stdlib.h>

typedef struct _btree{
  int v;
  struct _btree *l;
  struct _btree *r;
} BTree;

BTree* readBTree(int*);
int findInBTree(int, BTree*);

int main(int argc, char const *argv[]) {
  int dim;
  BTree* radix = readBTree(&dim);
  int in = 0;
  int out;
  while (in+1) {
    scanf("%d", &in);
    //if(in < 0) break; // Exit case
    out = findInBTree(in, radix);
    if(out+1) {
      printf("%d\n", out);
    } else {
      printf("NO\n");
    }
  }
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

int findInBTree(int val, BTree* radix) {
  if(radix == NULL) {
    return -1;
  }
  if(radix->v == val) {
    return 0;
  }
  int out;
  if(val < radix->v) {
    out = findInBTree(val, radix->l);
  } else {
    out = findInBTree(val, radix->r);
  }
  if(out >= 0) {
    return 1 + out;
  }
  return -1;
}
