#include <stdio.h>

int main(int argc, char const *argv[]) {
  int in = 1;
  int sum = 0;
  while(in) {
    scanf("%d", &in);
    sum += in;
  }
  printf("%d\n", sum);
  return 0;
}
