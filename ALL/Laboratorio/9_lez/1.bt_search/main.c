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

int bt_search(bt_node* node, int value) {
  int height = 0;
  while(node != NULL) {
    if(value < node->value) {
      node = node->left;
    } else if(value > node->value) {
      node = node->right;
    } else {
      return height;
    }
    height++;
  }
  return -1;
}

int main(int argc, char const *argv[]) {
  bt_node* radix = NULL;
  read_tree_b(&radix);
  int input;
  // Waiting for input < 0 to exit
  while(1) {
    scanf("%d", &input);
    if(input < 0) {
      break;                // Exit condition
    }
    int output = bt_search(radix, input);
    if(output >= 0) {
      printf("%d\n", output);
    } else {
      printf("NO\n");
    }
  }
  return 0;
}
