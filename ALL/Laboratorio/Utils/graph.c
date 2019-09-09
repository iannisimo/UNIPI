typedef struct {
  int visited;
  int arcs;
  int* adj;
} adj_arr_i;

// Read a graph and return an array of adj arrays
adj_arr_i* read_graph(int* n) {
  scanf("%d", n);
  adj_arr_i* adj_arrs = malloc(*n * sizeof(adj_arr_i));
  for(int i = 0; i < *n; i++) {
    int arcs;
    scanf("%d", &arcs);
    adj_arrs[i].arcs = arcs;
    adj_arrs[i].adj = malloc(arcs * sizeof(int));
    adj_arrs[i].visited = 0;
    for(int j = 0; j < arcs; j++) {
      int key;
      scanf("%d", &key);
      adj_arrs[i].adj[j] = key;
    }
  }
  return adj_arrs;
}

// DFS (Should be standard)
void dfs_visit(adj_arr_i* adj_arrs, int cur_vertex) {
  adj_arrs[cur_vertex].visited = 1;
  for(int i = 0; i < adj_arrs[cur_vertex].arcs; i++) {
    if(!adj_arrs[adj_arrs[cur_vertex].adj[i]].visited) {
      dfs_visit(adj_arrs, adj_arrs[cur_vertex].adj[i]);
    }
  }
  adj_arrs[cur_vertex].visited = 2;
}

void dfs(adj_arr_i* adj_arrs, int dim) {
  for(int i = 0; i < dim;  i++) {
    if(!adj_arrs[i].visited) {
      dfs_visit(adj_arrs, i);
    }
  }
}

// BFS (Needs a list to hold the Q(ueue) and a distance flag in the struct)
void reset_graph_visits(adj_arr_i* adj_arrs, int dim) {
  for(int i = 0; i < dim; i++) {
    adj_arrs[i].visited = 0;
    adj_arrs[i].distance = -1;
  }
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

void bfs(adj_arr_i* adj_arrs, int start) {
  node_m_i* queue_head = NULL;
  node_m_i* queue_tail = NULL;
  push_tt(&queue_head, &queue_tail, start);
  adj_arrs[start].distance = 0;
  int cur_node;
  while(queue_head != NULL) {
    cur_node = pop(&queue_head);
    for(int i = 0; i < adj_arrs[cur_node].arcs; i++) {
      int adj_node = adj_arrs[cur_node].adj[i];
      if(adj_arrs[adj_node].visited == 0) {
        adj_arrs[adj_node].visited = 1;
        adj_arrs[adj_node].distance = adj_arrs[cur_node].distance + 1;
        push_tt(&queue_head, &queue_tail, adj_node);
      }
    }
    adj_arrs[cur_node].visited = 2;
  }
}
