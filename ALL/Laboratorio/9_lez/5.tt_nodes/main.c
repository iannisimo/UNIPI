#include <stdio.h>
#include <stdlib.h>

typedef struct _tt_node {
  int value;
  struct _tt_node* left;
  struct _tt_node* center;
  struct _tt_node* right;
} tt_node;

void add_tree_t(tt_node** node, tt_node* new_node) {
  if(*node == NULL) {
    *node = new_node;
  } else {
    if(new_node->value < (*node)->value) {
      add_tree_t(&(*node)->left, new_node);
    } else if (!(new_node->value % (*node)->value)) {
      add_tree_t(&(*node)->center, new_node);
    } else {
      add_tree_t(&(*node)->right, new_node);
    }
  }
}

void read_tree_t(tt_node** radix) {
  int n;
  scanf("%d", &n);
  for(int i = 0; i < n; i++) {
    int value;
    scanf("%d", &value);
    tt_node* new_node = malloc(sizeof(tt_node));
    new_node->value = value;
    add_tree_t(radix, new_node);
  }
}

int count_tri_nodes(tt_node* node) {
  if(node == NULL) {
    return 0;
  }
  if(node->left != NULL && node->center != NULL && node->right != NULL) {
    return 1 + count_tri_nodes(node->left) + count_tri_nodes(node->center) + count_tri_nodes(node->right);
  }
  return 0 + count_tri_nodes(node->left) + count_tri_nodes(node->center) + count_tri_nodes(node->right);
}

int main(int argc, char const *argv[]) {
  tt_node* radix = NULL;
  read_tree_t(&radix);
  int result = count_tri_nodes(radix);
  printf("%d\n", result);
  return 0;
}
