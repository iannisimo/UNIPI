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

void collisions(node_m_i** table, int dim, int* maxCollLenght, int* nCollisions) {
  int max = 0;
  *nCollisions = 0;
  for(int i = 0; i < dim; i++) {
    int localMax = 0;
    node_m_i* ptr = table[i];
    while(ptr != NULL) {
      ptr = ptr->next;
      localMax++;
    }
    if (localMax > 0) *nCollisions += localMax - 1;
    if (localMax > max) max = localMax;
  }
  *maxCollLenght = max;
}


int main(int argc, char const *argv[]) {
  int n;
  node_m_i** table = read_hash_table(&n);
  int n_collisions;
  int m_collision_lenght;
  collisions(table, 2 * n, &m_collision_lenght, &n_collisions);
  printf("%d\n%d\n", m_collision_lenght, n_collisions);
  return 0;
}
