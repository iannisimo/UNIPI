#include <stdio.h>

void multipli(int n, int m, int arr[]) {
  int i;
  int j = 0;
  for(i = m; i < n; i+=m) {
    arr[j] = i;
    j++;
  }
  arr[j] = -1;
}

int main(int argc, char const *argv[]) {
  int n, m;
  scanf("%d %d", &n, &m);
  int out[n];
  multipli(n, m, out);
  int i = 0;
  while(out[i] != -1 && i < n) {
    printf("%d\n", out[i]);
    i++;
  }
  return 0;
}
