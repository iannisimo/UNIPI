// Graphs

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


// Reads

// Strings
typedef struct {
  char value[100];
  int frequency;
} string_f;

void readArr_strings(char** arr, int dim) {
  for(int i = 0; i < dim; i++) {
    arr[i] = malloc(100 * sizeof(char));
    scanf("%s", arr[i]);
  }
}

void readArr(char*** arr, int* n, int* k) {
  scanf("%d\n%d", n, k);
  *arr = malloc(*n * sizeof(char*));
  readArr_strings(*arr, *n);
}

// Strange structs
typedef struct {
  int x;
  int y;
  int c;
} point;

typedef struct {
  int x1;
  int x2;
  int y1;
  int y2;
} rect;

point* readPoints(int n) {
  point* out;
  out = malloc(n * sizeof(point));
  for(int i = 0; i < n; i++) {
    scanf("%d %d %d", &(out[i].x), &(out[i].y), &(out[i].c));
  }
  return out;
}

rect readRect() {
  rect out;
  scanf("%d %d %d %d", &(out.x1), &(out.y1), &(out.x2), &(out.y2));
  return out;
}

// Vect2D
int readArr_vect2d(vect2d** arr) {
  int out;
  scanf("%d", &out);
  *arr = malloc(sizeof(vect2d) * out);
  for(int i = 0; i < out; i++) {
    scanf("%d %d", &(*arr+i)->x, &(*arr+i)->y);
  }
  return out;
}


// QSorts

// Strings and String_f struct
int qsort_asc_string(const void* a, const void* b) {
  return -1 * strcmp(*(char**) a, *(char**)b);
}
int qsort_des_string_f_freq(const void* a, const void* b) {
  string_f* c = (string_f*) a;
  string_f* d = (string_f*) b;
  return d->frequency - c->frequency;
}
int qsort_des_string_f_val(const void* a, const void* b) {
  string_f* c = (string_f*) a;
  string_f* d = (string_f*) b;
  return strcmp(c->value, d->value);
}

// Vect2D
int qsort_vect2d(const void* a, const void* b) {
  vect2d* c = (vect2d*) a;
  vect2d* d = (vect2d*) b;
  if(c->x == d->x) {
    return d->y - c->y;
  }
  return c->x - d->x;
}

// Strange structs
typedef struct {
  int x;
  int y;
  int c;
} point;

int ascendingC(const void* a, const void* b) {
  return ((point*)a)->c - ((point*)b)->c;
}

typedef struct {
  char name[101];
  int credits;
  int difficulty;
} exam;

int qsort_exams(const void* a, const void* b) {
  exam* c = (exam*) a;
  exam* d = (exam*) b;
  if(c->credits == d->credits) {
    if(c->difficulty == d->difficulty) {
      return strcmp(c->name, d->name);
    }
    return c->difficulty - d->difficulty;
  }
  return d->credits - c->credits;
}
int qsort_exam_names(const void* a, const void* b) {
  exam* c = (exam*) a;
  exam* d = (exam*) b;
  return strcmp(c->name, d->name);
}


// Trees

typedef struct _bt_node {
  int value;
  struct _bt_node* left;
  struct _bt_node* right;
} bt_node;


// Read BT from stdin and put in struct
void add_tree_b(bt_node** node, bt_node* new_node) {
  if(*node == NULL) {
    *node = new_node;
  } else {
    if(new_node->value < (*node)->value) {
      add_tree_b(&(*node)->left, new_node);
    } else {
      add_tree_b(&(*node)->right, new_node);
    }
  }
}

int read_tree_b(bt_node** radix) {
  int dim;
  scanf("%d", &dim);
  for(int i = 0; i < dim; i++) {
    int value;
    scanf("%d", &value);
    bt_node* new_node = malloc(sizeof(bt_node));
    new_node->value = value;
    add_tree_b(radix, new_node);
  }
  return dim;
}

// Search key and return it's height
int bt_search(bt_node* node, int value) {
  int height = 0;
  while(node != NULL) {
    if(value < node->value) {
      node = node->left;
    } else if(value > node->value) {
      node = node->right;
    } else {
      return height;
    }
    height++;
  }
  return -1;
}

// Print BT max height
int max(int a, int b) {
  if(a > b) {
    return a;
  }
  return b;
}

int bt_height(bt_node* node) {
  if(node != NULL) {
    return 1 + max(bt_height(node->left), bt_height(node->right));
  }
  return 0;
}


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


// Hash tables with lists to handle coflicts

int get_hash(int value, int a, int b, int mod) {
  return ((value * a + b) % PRIME) % mod;
}

node_m_i** create_hash_table(int n) {
  node_m_i** table = malloc(2 * n * sizeof(node_m_i*));
  for(int i = 0; i < 2 * n; i++) {
    table[i] = NULL;
  }
  return table;
}

void hash_insert(node_m_i** node, int value) {
  if(*node == NULL) {
    *node = malloc(sizeof(node_m_i));
    (*node)->value = value;
    (*node)->next = NULL;
  } else {
    hash_insert(&((*node)->next), value);
  }
}

node_m_i** read_hash_table(int* n) {
  int a;
  int b;
  scanf("%d %d %d", n, &a, &b);
  node_m_i** table = create_hash_table(*n);
  int value;
  for(int i = 0; i < *n; i++) {
    scanf("%d", &value);
    int hash = get_hash(value, a, b, 2 * *n);
    hash_insert(&table[hash], value);
  }
  return table;
}


// GDB STUFF

file as stdin:
  run params ... < input_file
choose stack:
  select_frame #
  frame #
stop string at \0:
  set print null-stop on
print list with one command:
  define plist
    set var $n = $arg0
    while $n
      printf "%d -> ", $n->value
      set var $n = $n->next
    end
    printf "NULL\n"
  end
