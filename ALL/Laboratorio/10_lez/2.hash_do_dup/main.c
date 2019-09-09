#include <stdio.h>
#include <stdlib.h>

#define PRIME 999149

typedef struct _node_m_i {
  int value;
  struct _node_m_i* next;
} node_m_i;

int get_hash(int value, int a, int b, int mod) {
  return ((value * a + b) % PRIME) % mod;
}

node_m_i** create_hash_table(int n) {
  node_m_i** table = malloc(2 * n * sizeof(node_m_i*));
  for(int i = 0; i < 2 * n; i++) {
    table[i] = NULL;
  }
  return table;
}

void hash_insert(node_m_i** node, int value) {
  if(*node == NULL) {
    *node = malloc(sizeof(node_m_i));
    (*node)->value = value;
    (*node)->next = NULL;
  } else if((*node)->value == value){
    return;
  } else {
    hash_insert(&((*node)->next), value);
  }
}

node_m_i** read_hash_table(int* n) {
  int a;
  int b;
  scanf("%d %d %d", n, &a, &b);
  node_m_i** table = create_hash_table(*n);
  int value;
  for(int i = 0; i < *n; i++) {
    scanf("%d", &value);
    int hash = get_hash(value, a, b, 2 * *n);
    hash_insert(&table[hash], value);
  }
  return table;
}

void processOutput(node_m_i** table, int dim, int* a, int* b, int* c) {
  int maxLenght = 0;
  for(int i = 0; i < dim; i++) {
    int localLenght = 0;
    node_m_i* curNode = table[i];
    while(curNode != NULL) {
      localLenght++;
      if(curNode->next != NULL) (*a)++;
      curNode = curNode->next;
    }
    (*c) += localLenght;
    if(localLenght > maxLenght) maxLenght = localLenght;
  }
  *b = maxLenght;
}



int main(int argc, char const *argv[]) {
  int n;
  node_m_i** table = read_hash_table(&n);
  int n_collisions;
  int m_collision_lenght;

  int out1 = 0;
  int out2 = 0;
  int out3 = 0;
  processOutput(table, 2*n, &out1, &out2, &out3);
  printf("%d\n%d\n%d\n", out1, out2, out3);

  return 0;
}
