#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LENGTH 100

typedef struct {
  char value[MAX_LENGTH];
  int length;
} sstring;

int getLength_string(char* tmp, int maxLength) {
  int len = 0;
  while(len < maxLength && tmp[len] != '\0') {
    len++;
  }
  return len;
}

int readArr_sstring(sstring** arr) {
  int out;
  scanf("%d", &out);
  *arr = malloc(out * sizeof(sstring));
  for(int i = 0; i < out; i++) {
    scanf("%s", (*arr + i)->value);
    (*arr + i)->length = getLength_string((*arr + i)->value, MAX_LENGTH);
  }
}

int qsort_sstring(const void* a, const void* b) {
  sstring* c = (sstring*) a;
  sstring* d = (sstring*) b;
  if(c->length == d->length) {
    return strcmp(c->value, d->value);
  }
  return c->length - d->length;
}

void printArr_sstring(sstring* arr, int dim) {
  for(int i = 0; i < dim; i++) {
    printf("%s\n", arr[i].value);
  }
}

int main(int argc, char const *argv[]) {
  sstring* arr;
  int dim = readArr_sstring(&arr);
  qsort(arr, dim, sizeof(sstring), qsort_sstring);
  printArr_sstring(arr, dim);
  return 0;
}
