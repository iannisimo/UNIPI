#include <stdio.h>

int main(int argc, char const *argv[]) {
  int x, y, z;
  scanf("%d\n%d\n%d", &x, &y, &z);
  int max = x;
  if (y > max) max = y;
  if (z > max) max = z;
  printf("%d\n", max);
  return 0;
}
