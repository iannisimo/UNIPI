#include <stdio.h>
#include <stdlib.h>

typedef struct _node {
  int v;
  struct _node* n;
} node;

node* readList(int*);
node* insertAfter(node*, int);
void printList(node*);

int getAvg(node*, int);
void deleteLEQ(node**, int);

int main(int argc, char const *argv[]) {
  int n;
  node* list = readList(&n);
  int avg = getAvg(list, n);
  printf("%d\n", avg);
  printList(list);
  deleteLEQ(&list, avg);
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

int getAvg(node* list, int dim) {
  int sum = 0;
  while(list != NULL) {
    sum += list->v;
    list = list->n;
  }
  return sum / dim;
}

void deleteLEQ(node** list, int val) {
  if(*list == NULL) return;
  if((*list)->v <= val) {
    node* tmp = *list;
    *list = (*list)->n;
    free(tmp);
    deleteLEQ(list, val);
  } else {
    deleteLEQ(&(*list)->n, val);
  }
}
