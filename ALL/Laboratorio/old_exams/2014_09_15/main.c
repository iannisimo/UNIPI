#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

typedef struct _bt_node {
  int value;
  int left_nodes;
  int right_nodes;
  struct _bt_node* left;
  struct _bt_node* right;
} bt_node;

typedef struct {
  int* arr;
  int index;
} int_arr;

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

void add_prop(bt_node* node, int_arr* nodes_arr) {
  if(node == NULL) return;
  add_prop(node->left, nodes_arr);
  add_prop(node->right, nodes_arr);
  node->left_nodes = 0;
  node->right_nodes = 0;
  if(node->left != NULL) {
    node->left_nodes = node->left->left_nodes + 1;
  }
  if(node->right != NULL) {
    node->right_nodes = node->right->right_nodes + 1;
  }
  if(node->left_nodes > node->right_nodes) {
    (*nodes_arr).arr[nodes_arr->index] = node->value;
    (*nodes_arr).index += 1;
  }
}

int qsort_asc_int(const void* a, const void* b) {
  if(*(int*) a > *(int*) b) return 1;
  return -1;
}

void printArr_int(int* arr, int dim) {
  for(int i = 0; i < dim; i++) printf("%d\n", arr[i]);
}

int main(int argc, char const *argv[]) {
  bt_node* radix = NULL;
  int dim = read_tree_b(&radix);
  int_arr nodes;
  nodes.arr = malloc(dim * sizeof(int));
  nodes.index = 0;
  add_prop(radix, &nodes);
  qsort(nodes.arr, nodes.index, sizeof(int), qsort_asc_int);
  printArr_int(nodes.arr, nodes.index);
  return 0;
}
