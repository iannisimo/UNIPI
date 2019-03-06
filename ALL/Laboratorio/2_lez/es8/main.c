#include <stdio.h>

int strcmp(char *s1, char *s2);

int main(int argc, char const *argv[]) {
  char str1[1000], str2[1000];
  scanf("%s %s", str1, str2);
  int out = strcmp(str1, str2);
  if (out > 0) printf("+");
  printf("%d\n", out);
  return 0;
}

int strcmp(char *s1, char *s2) {
  int i = 0;
  int out = 0;

  do {
    if(s1[i] < s2[i]) {
      out = -1;
    }
    if(s1[i] > s2[i]) {
      out = 1;
    }
  } while(s1[i++] != '\0' && !out);
  return out;
}
