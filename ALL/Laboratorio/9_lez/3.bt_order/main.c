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

void print_tree_asc(bt_node* node) {
  if(node == NULL) return;
  print_tree_asc(node->left);
  printf("%d\n", node->value);
  print_tree_asc(node->right);
}

int main(int argc, char const *argv[]) {
  bt_node* radix = NULL;
  read_tree_b(&radix);
  print_tree_asc(radix);
  return 0;
}
