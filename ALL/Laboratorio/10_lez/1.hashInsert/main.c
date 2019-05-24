#include <stdio.h>
#include <stdlib.h>

#define PRIME 999149

typedef struct _node {
  int v;
  struct _node* n;
} node;

void resetTable(node**, int);
void readTable(node**, int, int, int);
int hashFun(int, int, int, int);
void tableInsert(node**, int);

void collisions(node**, int, int*, int*);

int main(int argc, char const *argv[]) {
  int n, a, b;
  scanf("%d %d %d", &n, &a, &b);
  node** table = malloc(2 * n * sizeof(node*));
  resetTable(table, n);
  readTable(table, a, b, n);

  int maxCollisionLenght;
  int numberOfCollisions;
  collisions(table, 2 * n, &maxCollisionLenght, &numberOfCollisions);
  printf("%d\n%d\n", maxCollisionLenght, numberOfCollisions);
  return 0;
}

void resetTable(node** table, int n) {
  for(int i = 0; i < n; i++) {
    table[i] = NULL;
  }
}

void readTable(node** table, int a, int b, int n) {
  for(int i = 0; i < n; i++) {
    int key;
    scanf("%d", &key);
    int index = hashFun(key, a, b, 2*n);
    tableInsert(&(table[index]), key); // <--
  }
}

int hashFun(int k, int a, int b, int mod) {
  return ((a * k + b) % PRIME) % mod;
}

void tableInsert(node** curNode, int key) {
  if(*curNode != NULL) {
    tableInsert(&((*curNode)->n), key);
  } else {
    (*curNode) = malloc(sizeof(node));
    (*curNode)->v = key;
    (*curNode)->n = NULL;
  }
}


void collisions(node** table, int dim, int* maxCollLenght, int* nCollisions) {
  int max = 0;
  *nCollisions = 0;
  for(int i = 0; i < dim; i++) {
    int localMax = 0;
    node* ptr = table[i];
    while(ptr != NULL) {
      ptr = ptr->n;
      localMax++;
    }
    if (localMax > 0) *nCollisions += localMax - 1;
    if (localMax > max) max = localMax;
  }
  *maxCollLenght = max;
}
