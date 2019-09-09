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

int main(int argc, char const *argv[]) {
  int n;
  adj_arr_i* adj_arrs = read_graph(&n);
  int ok = dfs(adj_arrs, n);
  printf("%d\n", ok);
  return 0;
}
