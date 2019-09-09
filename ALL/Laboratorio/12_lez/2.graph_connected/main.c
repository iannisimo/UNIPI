#include <stdio.h>
#include <stdlib.h>

typedef struct {
  int visited;
  int arcs;
  int* adj;
} adj_arr_i;

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

int dfs_visit(adj_arr_i* adj_arrs, int cur_vertex) {
  adj_arrs[cur_vertex].visited = 1;
  for(int i = 0; i < adj_arrs[cur_vertex].arcs; i++) {
    if(!adj_arrs[adj_arrs[cur_vertex].adj[i]].visited) {
      dfs_visit(adj_arrs, adj_arrs[cur_vertex].adj[i]);
    }
  }
  adj_arrs[cur_vertex].visited = 2;
}

int dfs(adj_arr_i* adj_arrs, int dim) {
  for(int i = 0; i < dim;  i++) {
    if(!adj_arrs[i].visited) {
      if(i) {
        return 0;
      }
      dfs_visit(adj_arrs, i);
    }
  }
  return 1;
}

int main(int argc, char const *argv[]) {
  int n;
  adj_arr_i* adj_arrs = read_graph(&n);
  int ok = dfs(adj_arrs, n);
  printf("%d\n", ok);
  return 0;
}

/*
6
2 1 3
2 0 2
2 1 5
2 0 4
1 3
1 2
*/
