#include <stdio.h>
#include <stdlib.h>

#define PRIME 999149

typedef struct _node {
  int v;
  struct _node* n;
} node;

node** resetTable(int);
node** readTable(int*, int*, int*);
int hash(int, int, int, int);
void hashInsert(node**, int);

void processOutput(node**, int, int*, int*, int*);

int main(int argc, char const *argv[]) {
  node** table;
  int n;
  int a;
  int b;
  table = readTable(&n, &a, &b);

  int out1 = 0;
  int out2 = 0;
  int out3 = 0;
  processOutput(table, 2*n, &out1, &out2, &out3);
  printf("%d\n%d\n%d\n", out1, out2, out3);

  return 0;
}

node** initTable(int n) {
  node** table = malloc(n * sizeof(node*));
  for(int i = 0; i < n; i++) {
    table[i] = NULL;
  }
  return table;
}


node** readTable(int* n, int* a, int* b) {
  scanf("%d %d %d", n, a, b);
  node** table = initTable(2**n);
  for(int i = 0; i < *n; i++) {
    int key;
    scanf("%d", &key);
    int index = hash(key, *a, *b, 2**n);
    hashInsert(&table[index], key);
  }
  return table;
}

int hash(int k, int a, int b, int mod) {
  return ((a*k + b) % PRIME) % mod;
}

void hashInsert(node** curNode, int key) {
  if(*curNode == NULL) {
    *curNode = malloc(sizeof(node));
    (*curNode)->n = NULL;
    (*curNode)->v = key;
    return;
  }
  if((*curNode)->v == key) return;
  hashInsert(&((*curNode)->n), key);
}

void processOutput(node** table, int dim, int* a, int* b, int* c) {
  int maxLenght = 0;
  for(int i = 0; i < dim; i++) {
    int localLenght = 0;
    node* curNode = table[i];
    while(curNode != NULL) {
      localLenght++;
      if(curNode->n != NULL) (*a)++;
      curNode = curNode->n;
    }
    (*c) += localLenght;
    if(localLenght > maxLenght) maxLenght = localLenght;
  }
  *b = maxLenght;
}
