// 13_06_2014
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char name[101];
  int credits;
  int difficulty;
} exam;

exam* read_exams(int* k, int* n) {
  scanf("%d\n%d", k, n);
  exam* exams = malloc(*n * sizeof(exam));
  for(int i = 0; i < *n; i++) {
    scanf("%s\n%d\n%d", exams[i].name, &exams[i].credits, &exams[i].difficulty);
  }
  return exams;
}

int qsort_exams(const void* a, const void* b) {
  exam* c = (exam*) a;
  exam* d = (exam*) b;
  if(c->credits == d->credits) {
    if(c->difficulty == d->difficulty) {
      return strcmp(c->name, d->name);
    }
    return c->difficulty - d->difficulty;
  }
  return d->credits - c->credits;
}
int qsort_exam_names(const void* a, const void* b) {
  exam* c = (exam*) a;
  exam* d = (exam*) b;
  return strcmp(c->name, d->name);
}

exam* remove_undoable(exam* all_exams, int *n, int k) {
  exam* doable = malloc(*n * sizeof(exam));
  int index = 0;
  for(int i = 0; i < *n; i++) {
    if(k - all_exams[i].credits >= 0) {
      k -= all_exams[i].credits;
      doable[index] = all_exams[i];
      index++;
    }
  }
  *n = index;
  return doable;
}

void print_exams(exam* exams, int n) {
  for(int i = 0; i < n; i++) {
    printf("%s\n", exams[i].name);
  }
}

int main(int argc, char const *argv[]) {
  int k;
  int n;
  exam* exams = read_exams(&k, &n);
  qsort(exams, n, sizeof(exam), qsort_exams);
  exam* doable_exams = remove_undoable(exams, &n, k);
  qsort(doable_exams, n, sizeof(exam), qsort_exam_names);
  print_exams(doable_exams, n);
  return 0;
}

// 27_06_2014
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char name[101];
  char number[21];
} ab_entry;

typedef struct _node_m_st {
  ab_entry entry;
  struct _node_m_st* next;
} node_m_st;

int hash_name(char* name, int mod) {
  int index = 0;
  int sum = 0;
  while(name[index] != '\0') {
    sum += name[index];
    index++;
  }
  return sum % (mod);
}

void hash_insert(node_m_st** node, ab_entry entry) {
  if(*node == NULL) {
    *node = malloc(sizeof(node_m_st));
    (*node)->entry = entry;
    (*node)->next = NULL;
  } else if(strcmp((*node)->entry.name, entry.name) > 0) {
    node_m_st* new_node = malloc(sizeof(node_m_st));
    new_node->entry = entry;
    new_node->next = (*node);
    (*node) = new_node;
  } else {
    hash_insert(&((*node)->next), entry);
  }
}

node_m_st** read_and_hash(int *k) {
  int n;
  scanf("%d", &n);
  node_m_st** entrys = malloc(sizeof(node_m_st*) * 2 * n);
  for(int i = 0; i < 2 * n; i++) entrys[i] = NULL;
  for(int i = 0; i < n; i++) {
    ab_entry tmp;
    scanf("%s %s", (&tmp)->name, (&tmp)->number);
    int hash = hash_name(tmp.name, 2 * n);
    hash_insert(&entrys[hash], tmp);
  }
  scanf("%d", k);
  return entrys;
}

void print_entrys(node_m_st* node) {
  while(node != NULL) {
    printf("%s %s\n", node->entry.name, node->entry.number);
    node = node->next;
  }
}

int main(int argc, char const *argv[]) {
  int k;
  node_m_st** entrys = read_and_hash(&k);
  print_entrys(entrys[k]);
  return 0;
}

// 15_09_2014
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

int qsort_asc_int(const void* a, const void* b) { // No, fare stampa simmetrica per restare lineare
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
  qsort(nodes.arr, nodes.index, sizeof(int), qsort_asc_int); // <-- NOOONOOONONOONON!!
  printArr_int(nodes.arr, nodes.index);
  return 0;
}

// 06_11_2014
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char type[101];
  int value;
} object;

typedef struct _node_m_st {
  object obj;
  struct _node_m_st* next;
} node_m_st;

int hash_name(char* name, int mod) {
  int index = 0;
  int sum = 0;
  while(name[index] != '\0') {
    sum += name[index];
    index++;
  }
  return sum % (mod);
}

void hash_insert(node_m_st** node, object obj) {
  if(*node == NULL) {
    *node = malloc(sizeof(node_m_st));
    (*node)->obj = obj;
    (*node)->next = NULL;
  } else if(!strcmp((*node)->obj.type, obj.type)) {
    if(obj.value > (*node)->obj.value) {
      (*node)->obj.value = obj.value;
    }
  } else {
    hash_insert(&(*node)->next, obj);
  }
}

node_m_st** read_and_hash_obj(int* n) {
  scanf("%d", n);
  node_m_st** objs = malloc(sizeof(node_m_st*) * 2 * *n);
  for(int i = 0; i < 2 * *n; i++) objs[i] = NULL;
  for(int i = 0; i < *n; i++) {
    object tmp;
    scanf("%s %d", (&tmp)->type, &tmp.value);
    int hash = hash_name(tmp.type, 2 * *n);
    // hash_insert(objs, tmp, hash);
    hash_insert(&objs[hash], tmp);
  }
  return objs;
}

object* table_to_array(node_m_st** objs, int n, int* new_n) {
  object* obj_arr = malloc(2 * n * sizeof(object));
  int index = 0;
  for(int i = 0; i < 2 * n; i++) {
    node_m_st* pointer = objs[i];
    while(pointer != NULL) {
      obj_arr[index] = pointer->obj;
      index++;
      pointer = pointer->next;
    }
  }
  *new_n = index;
  return obj_arr;
}

int qsort_obj(const void* a, const void* b) {
  object* c = (object*) a;
  object* d = (object*) b;
  if(c->value == d->value) {
    return strcmp(c->type, d->type);
  }
  return d->value - c->value;
}

void print_objs(object* obj_arr, int n) {
  for(int i = 0; i < n && i < 15; i++) {
    printf("%s\n", obj_arr[i].type);
  }
}

int main(int argc, char const *argv[]) {
  int n;
  int m;
  node_m_st** objects = read_and_hash_obj(&n);
  object* obj_arr = table_to_array(objects, n, &m);
  qsort(obj_arr, m, sizeof(object), qsort_obj);
  print_objs(obj_arr, m);
  return 0;
}

// 17_02_2015
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
