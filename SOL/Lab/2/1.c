#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
  if (argc != 3) {
    fprintf(stderr, "use: %s stringa1 stringa2\n", argv[0]);
    return -1;
  }
  char* save = NULL;
  char* token1 = strtok_r(argv[1], " ", &save);

  while (token1) {
    printf("%s\n", token1);
    char* token2 = strtok(argv[2], " ");
    while(token2) {
      printf("%s\n", token2);
      token2 = strtok(NULL, " ");
    }
    token1 = strtok_r(NULL, " ", &save);
  }
  return 0;
}
