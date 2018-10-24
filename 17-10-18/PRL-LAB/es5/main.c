#include <stdio.h>

float approx_pi(int n) {
  int i;
  int sign = 1; // 0: -    1: +
  float pi = 0;
  for (i = 0; i < n; i++) {
    if(sign) {
      pi += ((4.0)/((2*i)+1));
      sign = 0;
    } else {
      pi -= ((4.0)/((2*i)+1));
      sign = 1;
    }
  }
  return pi;
}

int main(int argc, char const *argv[]) {
  int n;
  scanf("%d", &n);
  printf("%.6f\n", approx_pi(n));
  return 0;
}
