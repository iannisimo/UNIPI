#include <stdio.h>

int isPrime(int val) {
  int div = 2;
  int found = !(val % div);
  printf("%d\n", found);
  div++;
  while(!found && div < val) {
    found = !(val % div);
    div += 2;
  }
  return !found;
}

int main(int argc, char const *argv[]) {
  int in;
  scanf("%d", &in);
  printf("%d\n", isPrime(in));
  return 0;
}
