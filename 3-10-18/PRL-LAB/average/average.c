#include <stdio.h>

int main(int argc, char const *argv[]) {
  float x, y, z;
  scanf("%f\n%f\n%f", &x, &y, &z);
  float out = (x + y + z) / 3;
  printf("%.3f\n", out);
  return 0;
}
