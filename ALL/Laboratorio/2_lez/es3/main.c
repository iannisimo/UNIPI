#include <stdio.h>

int my_strlen (char *s);

int main (int argc, char const *argv[]) {
  char str[1000];
  scanf("%s", str);
  printf("%d\n", my_strlen(str));
  return 0;
}

int my_strlen (char *s) {
  int i = 0;
  while(s[i] != '\0') i++;
  return i;
}
