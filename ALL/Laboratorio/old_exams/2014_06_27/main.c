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

void insert_node_before(node_m_st** node, ab_entry entry) {

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

/*
4
Gianni
1
Carlo
2
Maria
3
Antonella
4
6
*/
