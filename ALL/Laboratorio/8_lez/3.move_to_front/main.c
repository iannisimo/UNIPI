#include <stdio.h>
#include <stdlib.h>

typedef struct Snode_m_i {
  int value;
  struct Snode_m_i* next;
} node_m_i;

int readList_mono_tail(node_m_i** head) {
  int n;
  scanf("%d", &n);
  node_m_i* pointer;
  for(int i = 0; i < n; i++) {
    int v;
    scanf("%d", &v);
    if(*head == NULL) {
      *head = malloc(sizeof(node_m_i));
      (*head)->value = v;
      (*head)->next = NULL;
      pointer = *head;
    } else {
      pointer->next = malloc(sizeof(node_m_i));
      pointer = pointer->next;
      pointer->value = v;
      pointer->next = NULL;
    }
  }
  return n;
}

int move_to_front(node_m_i** head, int v) {
  int index = 0;
  node_m_i* prec = NULL;
  node_m_i* pointer = *head;
  while(pointer != NULL) {
    if(pointer->value == v) {
      if(prec != NULL) {
        node_m_i* tmp = pointer->next;
        pointer->next = *head;
        *head = pointer;
        prec->next = tmp;
      }
      return index;
    } else {
      index++;
      prec = pointer;
      pointer = pointer->next;
    }
  }
  return -1;
}

int main(int argc, char const *argv[]) {
  node_m_i* list = NULL;
  readList_mono_tail(&list);
  int output = 0;
  while(++output) {
    int input;
    scanf("%d", &input);
    output = move_to_front(&list, input);
    printf("%d\n", output);
  }
  return 0;
}
