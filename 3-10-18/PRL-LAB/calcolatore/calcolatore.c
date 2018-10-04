#include <stdio.h>

int main(int argc, char const *argv[]) {
  float a, b, out;
  char c;
  scanf("%f\n%f\n%c", &a, &b, &c);
  switch (c) {
    case '+':
      out = a + b;
      break;
    case '-':
      out = a - b;
      break;
    case '/':
      out = a / b;
      break;
    case '%':
      out = (int) a % (int) b;
  }
  printf("%.1f\n", out);
  return 0;
}
