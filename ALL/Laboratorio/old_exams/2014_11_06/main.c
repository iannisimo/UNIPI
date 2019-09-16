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
