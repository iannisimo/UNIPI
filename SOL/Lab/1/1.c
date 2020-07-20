#include <stdarg.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define REALLOC_INC 16
#define RIALLOCA(buf, newsize) \
    buf = realloc(buf, newsize * sizeof(char) * 101)

char* mystrcat(char *buf, size_t sz, char *first, ...) {
  strncat(buf, first, 100);
  va_list ap;
  va_start(ap, first);
  int i = REALLOC_INC;
  while(i) {
    char* aux = va_arg(ap, char*);
    if(aux == NULL) break;
    strncat(buf, aux, 100);
    i--;
  }
  va_end(ap);
  return buf;
}

int main(int argc, char *argv[]) {
  if (argc != 7) { printf("troppi pochi argomenti\n"); return -1; }
  char *buffer=NULL;
  RIALLOCA(buffer, REALLOC_INC);  // macro che effettua l'allocazione
  buffer[0]='\0';
  buffer = mystrcat(buffer, REALLOC_INC, argv[1], argv[2], argv[3], argv[4], argv[5], argv[6], NULL);
  printf("%s\n", mystrcat(buffer, 16, "prima stringa", "seconda", "terza molto molto molto lunga", "quarta", "quinta lunga", "ultima!",NULL));

  free(buffer);
  return 0;
}
