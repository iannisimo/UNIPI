#include <stdio.h>
#include <stdlib.h>

typedef struct Snode_b_i {
  int value;
  int counter;
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
      (*head)->counter = 0;
      (*head)->next = NULL;
      (*head)->prev = NULL;
      pointer = *head;
    } else {
      pointer->next = malloc(sizeof(node_b_i));
      pointer->next->prev = pointer;
      pointer = pointer->next;
      pointer->value = v;
      pointer->counter = 0;
      pointer->next = NULL;
    }
  }
}

void node_b_swap(node_b_i** head, node_b_i* curr) {
  node_b_i* oldPrev = curr->prev;
  curr->prev = oldPrev->prev;
  oldPrev->next = curr->next;
  oldPrev->prev = curr;
  if(curr->next != NULL) {
    curr->next->prev = oldPrev;
  }
  if(curr->prev != NULL) {
    curr->prev->next = curr;
  }
  curr->next = oldPrev;
  if(curr->prev == NULL) {
    *head = curr;
  }
}

// void node_b_swap(node_b_i* node1, node_b_i* node2) {
//   int tmp_value = node1->value;
//   int tmp_counter = node1->counter;
//   node1->value = node2->value;
//   node1->counter = node2->counter;
//   node2->value = tmp_value;
//   node2->counter = tmp_counter;
// }

int search_and_count(node_b_i** head, int v) {
  int index = 0;
  node_b_i* pointer = *head;
  while(pointer != NULL) {
    if(pointer->value == v) {
      pointer->counter++;
      while(pointer->prev != NULL && pointer->counter > pointer->prev->counter) {
        node_b_swap(head, pointer);
      }
      return index;
    }
    index++;
    pointer = pointer->next;
  }
  return -1;
}

int main(int argc, char const *argv[]) {
  node_b_i* list = NULL;
  readList_bi_tail(&list);
  int output = 0;
  while(++output) {
    int input;
    scanf("%d", &input);
    output = search_and_count(&list, input);
    printf("%d\n", output);
  }
  return 0;
}
