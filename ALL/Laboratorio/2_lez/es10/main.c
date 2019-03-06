#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* my_strcpy(char *dest, char *src);
char* my_strcat(char *s1, char *s2);
char* product(char *str, int k);

int main() {
  char* str = malloc(sizeof(char) * 0);
  int len;

  scanf("%s %d", str, &len);
  char* out = product(str, len);
  printf("%s\n", out);
  free(out);
  return 0;
}

char* my_strcpy(char *dest, char *src) {
  char *tmp = dest;

	while ((*dest++ = *src++) != '\0');
	return tmp;
}

char* my_strcat(char *s1, char *s2) {
  int i = 0;
  i = 0;
  while(s1[i++] != '\0');
  my_strcpy(&(s1[i - 1]), s2);
  return s1;
}

char* product(char *str, int k) {
  int i = 0;
  while(str[i++] != '\0');
  char* out = malloc(0);
  char* tmp = malloc(10000);
  for(int j = 0; j < k; j++) {
    my_strcat(out, str);
  }
  return out;
}
