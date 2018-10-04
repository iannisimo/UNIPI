#include <stdio.h>

int main(int argc, char const *argv[]) {
  float in;
  scanf("%f", &in);
  float out = (float) in * 1.8 + 32;
  printf("%.2f\n", out);
  return 0;
}
