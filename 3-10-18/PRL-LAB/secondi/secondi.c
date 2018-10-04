#include <stdio.h>

int main(int argc, char const *argv[]) {
  int in;
  scanf("%d", &in);
  int h, m, s;
  h = in / 3600;
  m = (in / 60) % 60;
  s = in % 60;
  printf("%d h %d min %d s\n", h, m, s);
  return 0;
}
