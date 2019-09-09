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

void read_tree_b(bt_node** radix1, bt_node** radix2, int* n, int* k) {
  scanf("%d %d", n, k);
  for(int i = 0; i < *n; i++) {
    int value;
    scanf("%d", &value);
    bt_node* new_node = malloc(sizeof(bt_node));
    new_node->value = value;
    add_tree_b(radix1, new_node);
  }
  for(int i = 0; i < *n; i++) {
    int value;
    scanf("%d", &value);
    bt_node* new_node = malloc(sizeof(bt_node));
    new_node->value = value;
    add_tree_b(radix2, new_node);
  }
}

int compare_tree_to(int k, bt_node* node1, bt_node* node2) {
  if(node1 == NULL || node2 == NULL) {
    return 0;
  }
  if(node1->value != node2->value) {
    return 0;
  }
  if(node1->value == k) {
    return 1;
  }
  if(k < node1->value) {
    return compare_tree_to(k, node1->left, node2->left);
  }
  return compare_tree_to(k, node1->right, node2->right);
}

int main(int argc, char const *argv[]) {
  int n;
  int k;
  bt_node* radix_a = NULL;
  bt_node* radix_b = NULL;
  read_tree_b(&radix_a, &radix_b, &n, &k);
  int result = compare_tree_to(k, radix_a, radix_b);
  printf("%d\n", result);
  return 0;
}
