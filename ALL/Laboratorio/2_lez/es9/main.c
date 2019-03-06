#include <stdio.h>
#include <stdlib.h>

char* my_strcpy(char *dest, char *src);

int main(int argc, char const *argv[]) {
  char str[1000];

  scanf("%s", str);
  int i = 0;
  while(str[i++] != '\0');
  char* out = malloc(sizeof(char) * i);
  my_strcpy(out, str);
  printf("%s\n", out);
  return 0;
}

char* my_strcpy(char *dest, char *src) {
  char *tmp = dest;

	while ((*dest++ = *src++) != '\0')
		/* nothing */;
	return tmp;
}
