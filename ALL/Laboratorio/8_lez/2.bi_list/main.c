#include <stdio.h>
#include <stdlib.h>

typedef struct Snode_b_i {
  int value;
  struct Snode_b_i* next;
  struct Snode_b_i* prev;
} node_b_i;

int readList_bi_tail(node_b_i** head) {
  int n;
  scanf("%d", &n);
  node_b_i* pointer;
  for(int i = 0; i < n; i++) {
    int v;
    scanf("%d", &v);
    if(*head == NULL) {
      *head = malloc(sizeof(node_b_i));
      (*head)->value = v;
      (*head)->next = NULL;
      (*head)->prev = NULL;
      pointer = *head;
    } else {
      pointer->next = malloc(sizeof(node_b_i));
      pointer->next->prev = pointer;
      pointer = pointer->next;
      pointer->value = v;
      pointer->next = NULL;
    }
  }
}

void printList_bi_inv(node_b_i* list) {
  while(list->next != NULL)
    list = list->next;
  while(list != NULL) {
    printf("%d\n", list->value);
    list = list->prev;
  }
}

int main(int argc, char const *argv[]) {
  node_b_i* list = NULL;
  readList_bi_tail(&list);
  printList_bi_inv(list);
  return 0;
}
