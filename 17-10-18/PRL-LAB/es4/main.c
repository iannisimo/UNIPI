#include <stdio.h>

int MCD(int n, int d) {
  int r;
  if(d > n) {
    int t = n;
    n = d;
    d = t;
  }
  r = n % d;
  while (r != 0) {
    n = d;
    d = r;
    r = n % d;
  }
  return d;
}

int mcm(int n, int m) {
  return (n * m) / MCD(n, m);
}

int main(int argc, char const *argv[]) {
  int n, m;
  scanf("%d %d", &n, &m);
  printf("%d\n", MCD(n, m));
  printf("%d\n", mcm(n, m));
  return 0;
}
