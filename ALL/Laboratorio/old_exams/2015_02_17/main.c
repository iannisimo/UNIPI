#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

typedef struct _bt_node {
  int value;
  struct _bt_node* left;
  struct _bt_node* right;
} bt_node;

typedef struct {
  int min_val;
  int route_max;
} route_values;

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

int min(int a, int b) {
  if(a < b) {
    return a;
  }
  return b;
}

route_values get_min_maxRoute(bt_node* node) {
  if(node == NULL) {
    return (route_values) {INT_MAX, 0};
  }
  route_values left = get_min_maxRoute(node->left);
  route_values right = get_min_maxRoute(node->right);
  int left_route = left.route_max + node->value;
  int right_route = right.route_max + node->value;
  if(left_route >= right_route) {
    return (route_values) {min(left.min_val, node->value), left_route};
  }
  return (route_values) {min(right.min_val, node->value), right_route};
}

int main(int argc, char const *argv[]) {
  bt_node* radix = NULL;
  if(!read_tree_b(&radix)) {
    printf("0\n");                                                                            // Edge-case handling
    return 0;
  }
  int min_maxRoute = get_min_maxRoute(radix).min_val;
  printf("%d\n", min_maxRoute);
  return 0;
}
