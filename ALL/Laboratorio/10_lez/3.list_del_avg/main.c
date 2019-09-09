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

int get_avg(node_m_i* node) {
  int sum = 0;
  int dim = 0;
  while(node != NULL) {
    dim++;
    sum += node->value;
    node = node->next;
  }
  return sum / dim;
}

void list_keep_over_avg(node_m_i** node, int avg) {
  if(*node == NULL) return;
  if((*node)->value <= avg) {
    node_m_i* tmp = *node;
    *node = tmp->next;
    free(tmp);
    list_keep_over_avg(node, avg);
  } else {
    list_keep_over_avg(&((*node)->next), avg);
  }
}

void printList_mono_r(node_m_i* list) {
  if(list != NULL) {
    printf("%d ", list->value);
    printList_mono_r(list->next);
  } else {
    printf("\n");
  }
}

int main(int argc, char const *argv[]) {
  node_m_i* list = NULL;
  readList_mono_tail(&list);
  int avg = get_avg(list);
  printf("%d\n", avg);
  printList_mono_r(list);
  list_keep_over_avg(&list, avg);
  printList_mono_r(list);
  return 0;
}
