#include <stdio.h>

float sum_pow(int n, float x) {
  int i, j;
  float out = 0.0;
  for(i = 0; i <= n; i++) {
    float tmpPow = 1.0;
    for(j = 0; j < i; j++) {
      tmpPow *= x;
    }
    out += tmpPow;
  }
  return out;
}

int main(int argc, char const *argv[]) {
  int n;
  float x;
  scanf("%d %f", &n, &x);
  printf("%.2f\n", sum_pow(n, x));
  return 0;
}
