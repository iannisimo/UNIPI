// Lists

typedef struct _node_m_i {
  int value;
  struct _node_m_i* next;
} node_m_i;

// Read from stdin and insert in monodirectional list to the tail
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

// print list monodirectional with recursive function
void printList_mono_r(node_m_i* list) {
  if(list != NULL) {
    printf("%d ", list->value);
    printList_mono_r(list->next);
  } else {
    printf("\n");
  }
}


// Delete selected list elements
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
