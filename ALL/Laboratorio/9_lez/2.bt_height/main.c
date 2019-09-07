#include <stdio.h>
#include <stdlib.h>

typedef struct _bt_node {
  int value;
  struct _bt_node* left;
  struct _bt_node* right;
} bt_node;

void add_tree_b(bt_node** node, bt_node* new_node) {
  if(*node == NULL) {
    *node = new_node;
  } else {
    if(new_node->value < (*node)->value) {
      add_tree_b(&(*node)->left, new_node);
    } else {
      add_tree_b(&(*node)->right, new_node);
    }
  }
}

int read_tree_b(bt_node** radix) {
  int dim;
  scanf("%d", &dim);
  for(int i = 0; i < dim; i++) {
    int value;
    scanf("%d", &value);
    bt_node* new_node = malloc(sizeof(bt_node));
    new_node->value = value;
    add_tree_b(radix, new_node);
  }
  return dim;
}

int max(int a, int b) {
  if(a > b) {
    return a;
  }
  return b;
}

int bt_height(bt_node* node) {
  if(node != NULL) {
    return 1 + max(bt_height(node->left), bt_height(node->right));
  }
  return 0;
}

int main(int argc, char const *argv[]) {
  bt_node* radix = NULL;
  read_tree_b(&radix);
  printf("%d\n", bt_height(radix));
  return 0;
}
