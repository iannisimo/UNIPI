#include <stdio.h>
#include <stdlib.h>

typedef struct _btree {
  int v;
  struct _btree *l;
  struct _btree *r;
} btree;

btree* readBTree(int);
btree* treeInsert(btree*, int);

int compareTree(btree*, btree*, int);


int main(int argc, char const *argv[]) {
  int n, k;
  scanf("%d %d", &n, &k);
  btree* tree1 = readBTree(n);
  btree* tree2 = readBTree(n);

  int result = compareTree(tree1, tree2, k);

  printf("%d\n", result);

  return 0;
}

btree* readBTree(int n) {
  btree* out = NULL;
  int in;
  for(int i = 0; i < n; i++) {
    scanf("%d", &in);
    out = treeInsert(out, in);
  }
  return out;
}

btree* treeInsert(btree* root, int val) {
  if(root == NULL) {
    root = malloc(sizeof(btree));
    root->v = val;
    root->l = NULL;
    root->r = NULL;
    return root;
  }
  if(val < root->v) {
    root->l = treeInsert(root->l, val);
  } else {
    root->r = treeInsert(root->r, val);
  }
  return root;
}

int compareTree(btree* treeA, btree* treeB, int key) {
  if(treeA == NULL || treeB == NULL) return 0;
  if(treeA->v != treeB->v) return 0;
  if(treeA->v == key && treeB->v == key) return 1;
  if(key < treeA->v) return compareTree(treeA->l, treeB->l, key);
  else return compareTree(treeA->r, treeB->r, key);
  return 0;
}
