#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char* value;
  int frequency;
} string;

char** readArrayString(int);
int orderCString(const void*, const void*);
int orderStringFreq(const void*, const void*);
int orderStringVal(const void*, const void*);

int main(int argc, char const *argv[]) {
  int n;
  int k;
  char** cstringArr;
  string* strings;

  scanf("%d %d", &n, &k);
  strings = (string*) malloc(n * sizeof(string));
  cstringArr = readArrayString(n);

  qsort(cstringArr, n, sizeof(char*), orderCString);

  int j = -1;
  for (int i = 0; i < n; i++) {
    if (j >= 0 && !strcmp(strings[j].value, cstringArr[i])) {
      strings[j].frequency++;
    } else {
      j++;
      strings[j].value = cstringArr[i];
      strings[j].frequency = 1;
    }
  }

  qsort (strings, j+1, sizeof(string), orderStringFreq);
  qsort (strings, k, sizeof(string), orderStringVal);

  for (int i = 0; i < k; i++ ) {
    printf("%s\n", strings[i].value);
  }
  return 0;
}

char** readArrayString(int dim) {
  char** arr = malloc(dim * sizeof(char*));
  for(int i = 0; i < dim; i++){
    arr[i] = malloc(100 * sizeof(char));
    scanf("%s", arr[i]);
  }
  return arr;
}

int orderCString(const void* a, const void* b) {
  return strcmp(*(char**) a, *(char**) b);
}

int orderStringFreq(const void* a, const void* b) {
  return ((string *) b)->frequency - ((string *) a)->frequency;
}

int orderStringVal(const void* a, const void* b) {
  return strcmp(((string *) a)->value, ((string *) b)->value);
}
