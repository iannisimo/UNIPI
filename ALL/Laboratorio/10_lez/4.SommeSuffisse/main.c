#include <stdio.h>
#include <stdlib.h>

typedef struct _node {
  int v;
  struct _node* n;
} node;

node* readList(int*);
node* insertAfter(node*, int);
void printList(node*);

int sumAll(node*);
void compute(node*, int);

int main(int argc, char const *argv[]) {
  int n;
  node* list = readList(&n);
  printList(list);
  int sum = sumAll(list);
  compute(list, sum);
  printList(list);
  return 0;
}

node* readList(int* n) {
  scanf("%d", n);
  node* head = NULL;
  node* tail = NULL;
  for(int i = 0; i < *n; i++) {
    int in;
    scanf("%d", &in);
    tail = insertAfter(tail, in);
    if(head == NULL) head = tail;
  }
  return head;
}

node* insertAfter(node* tail, int val) {
  if(tail == NULL) {
    tail = malloc(sizeof(node));
    tail->v = val;
    tail->n = NULL;
  } else {
    tail->n = malloc(sizeof(node));
    tail = tail->n;
    tail->v = val;
    tail->n = NULL;
  }
  return tail;
}

void printList(node* list) {
  if(list == NULL) {
    printf("\n");
    return;
  }
  printf("%d ", list->v);
  printList(list->n);
}

int sumAll(node* ptr) {
  if(ptr == NULL) return 0;
  return ptr->v + sumAll(ptr->n);
}

void compute(node* ptr, int sum) {
  if(ptr == NULL) return;
  int val = sum - ptr->v;
  ptr->v = val;
  compute(ptr->n, val);
}
