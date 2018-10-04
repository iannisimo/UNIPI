#include <stdio.h>

int main(int argc, char const *argv[]) {
  int x;
  scanf("%d", &x);
  printf("%d\n", (x + 1) % 2);
  return 0;
}
