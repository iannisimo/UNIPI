#include <stdio.h>
#include <stdlib.h>

typedef struct {
  int visited;
  int distance;
  int arcs;
  int* adj;
} adj_arr_i;

typedef struct _node_m_i {
  int value;
  struct _node_m_i* next;
} node_m_i;

void reset_graph_visits(adj_arr_i* adj_arrs, int dim) {
  for(int i = 0; i < dim; i++) {
    adj_arrs[i].visited = 0;
    adj_arrs[i].distance = -1;
  }
}

adj_arr_i* read_graph(int* n) {
  scanf("%d", n);
  adj_arr_i* adj_arrs = malloc(*n * sizeof(adj_arr_i));
  for(int i = 0; i < *n; i++) {
    int arcs;
    scanf("%d", &arcs);
    adj_arrs[i].arcs = arcs;
    adj_arrs[i].adj = malloc(arcs * sizeof(int));
    for(int j = 0; j < arcs; j++) {
      int key;
      scanf("%d", &key);
      adj_arrs[i].adj[j] = key;
    }
  }
  return adj_arrs;
}

int pop(node_m_i** list) {
  node_m_i* tmp = *list;
  int value = tmp->value;
  *list = (*list)->next;
  free(tmp);
  return value;
}

void push_tt(node_m_i** head, node_m_i** tail, int value) {
  node_m_i* new = malloc(sizeof(node_m_i));
  new->value = value;
  new->next = NULL;
  if(*head == NULL) {
    *head = new;
    *tail = new;
  } else {
    (*tail)->next = new;
    *tail = new;
  }
}

void printList_mono_r(node_m_i* list) {
  if(list != NULL) {
    printf("%d -> ", list->value);
    printList_mono_r(list->next);
  } else {
    printf("NULL\n");
  }
}

void bfs(adj_arr_i* adj_arrs, int start, int end) {
  node_m_i* queue_head = NULL;
  node_m_i* queue_tail = NULL;
  push_tt(&queue_head, &queue_tail, start);
  adj_arrs[start].distance = 0;
  while(queue_head != NULL) {
    int cur_node = pop(&queue_head);
    for(int i = 0; i < adj_arrs[cur_node].arcs; i++) {
      int adj_node = adj_arrs[cur_node].adj[i];
      if(adj_arrs[adj_node].visited == 0) {
        adj_arrs[adj_node].visited = 1;
        adj_arrs[adj_node].distance = adj_arrs[cur_node].distance + 1;
        push_tt(&queue_head, &queue_tail, adj_node);
      }
    }
    adj_arrs[cur_node].visited = 2;
    if(cur_node == end) {
      return;
    }
  }
}

int main(int argc, char const *argv[]) {
  int n;
  adj_arr_i* adj_arrs = read_graph(&n);
  int m;
  scanf("%d", &m);
  for(int i = 0; i < m; i++) {
    int start;
    int end;
    scanf("%d %d", &start, &end);
    reset_graph_visits(adj_arrs, n);
    bfs(adj_arrs, start, end);
    printf("%d\n", adj_arrs[end].distance);
    //printf("%d\n", bfs(adj_arrs, start, end));
  }
  return 0;
}

/*
7
2 1 3
2 0 2
2 1 5
2 0 4
1 2
1 2
6 0 1 2 3 4 5
4
0 4
5 0
3 3
2 6

8
2 1 2
1 3
2 4 5
0
2 5 6
1 7
1 7
0
1
0 1
*/
