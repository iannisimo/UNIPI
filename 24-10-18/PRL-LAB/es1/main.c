#include <stdio.h>

void diff_abs(float *a, float *b) {
  float tmp = *a - *b;
  *b = *b - *a;
  *a = tmp;
}

int main(int argc, char const *argv[]) {
  float n, m;
  scanf("%f %f", &n, &m);
  diff_abs(&n, &m);
  printf("%.2f\n%.2f", n, m);
  return 0;
}
