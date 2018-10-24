#include <stdio.h>

// void swap(int *a, int *b) {
//   int tmp = *a;
//   *a = *b;
//   *b = tmp;
// }

void swap(int *a, int *b) {
  *a = *a + *b;
  *b = *a - *b;
  *a = *a - *b;
}

void ordered_swap(int *a, int *b, int *c) {
  if(*a > *b) {
    swap(a, b);
  }
  if(*b > *c) {
    swap(b, c);
  }
  if(*a > *b) {
    swap(a, b);
  }
}

int main(int argc, char const *argv[]) {
  int n, m, o;
  scanf("%d %d %d", &n, &m, &o);
  ordered_swap(&n, &m, &o);
  printf("%d\n%d\n%d", n, m, o);
  return 0;
}
