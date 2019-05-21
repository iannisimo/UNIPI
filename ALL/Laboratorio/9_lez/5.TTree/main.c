#include <stdio.h>
#include <stdlib.h>

typedef struct _ttree {
  int v;
  struct _ttree *l;
  struct _ttree *c;
  struct _ttree *r;
} ttree;

ttree* readTTree(int*);
ttree* treeInsert(ttree*, int);
int countThreeNodes(ttree*);

int main(int argc, char const *argv[]) {
  int n;
  ttree* tree = readTTree(&n);

  int result = countThreeNodes(tree);

  printf("%d\n", result);

  return 0;
}

ttree* readTTree(int* n) {
  ttree* out = NULL;
  scanf("%d", n);
  int in;
  for(int i = 0; i < *n; i++) {
    scanf("%d", &in);
    out = treeInsert(out, in);
  }
  return out;
}

ttree* treeInsert(ttree* root, int val) {
  if(root == NULL) {
    root = malloc(sizeof(ttree));
    root->v = val;
    root->l = NULL;
    root->c = NULL;
    root->r = NULL;
    return root;
  }
  if(val < root->v) {
    root->l = treeInsert(root->l, val);
  } else if (!(val % root->v)) {
    root->c = treeInsert(root->c, val);
  } else {
    root->r = treeInsert(root->r, val);
  }
  return root;
}

int countThreeNodes(ttree* node) {
  if(node == NULL) return 0;
  int out = 0;
  if(
       node->l != NULL
    && node->c != NULL
    && node->r != NULL) {
    out = 1;
  }
  return out + countThreeNodes(node->l) + countThreeNodes(node->c) + countThreeNodes(node->r);
}
