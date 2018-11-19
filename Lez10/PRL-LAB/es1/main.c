#include <stdio.h>

int Pot2(int n) {
  int out = 2;
  if(n > 1) {
    out *= Pot2(n-1);
  }
  return out;
}

int main(int argc, char const *argv[]) {
  int in;
  scanf("%d", &in);
  printf("%d\n", Pot2(in));
  return 0;
}
