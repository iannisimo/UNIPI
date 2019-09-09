#include <stdio.h>
#include <stdlib.h>

typedef struct _node_m_i {
  int value;
  struct _node_m_i* next;
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

int compute(node_m_i* list) {
  if(list == NULL) return 0;
  int suff_sum = compute(list->next);
  int tmp = list->value;
  list->value = suff_sum;
  return suff_sum + tmp;
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
  printList_mono_r(list);
  compute(list);
  printList_mono_r(list);
  return 0;
}
