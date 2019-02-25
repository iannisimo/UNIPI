#include <stdio.h>
/*
 * a --> A: ASCII - 32
*/
int main(int argc, char const *argv[]) {
  int in;
  scanf("%c", &in);
  printf("%c\n", in - 32);
  return 0;
}
