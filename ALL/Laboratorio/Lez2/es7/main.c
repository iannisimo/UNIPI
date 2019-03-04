#include <stdio.h>
#include <string.h>

char* my_strcat(char *s1, char *s2);

int main(int argc, char const *argv[]) {
  char str1[1000], str2[1000];

  scanf("%s %s", str1, str2);
  my_strcat(str1, str2);
  printf("%s\n", str1);
  return 0;
}

char* my_strcat(char *s1, char *s2) {
  int i = 0;
  i = 0;
  while(s1[i++] != '\0');
  strcpy(&(s1[i - 1]), s2);
  return s1;
}
