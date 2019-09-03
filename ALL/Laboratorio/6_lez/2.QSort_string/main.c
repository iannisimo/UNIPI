#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void readArr_strings(char** arr, int dim) {
  for(int i = 0; i < dim; i++) {
    arr[i] = malloc(100 * sizeof(char));
    scanf("%s", arr[i]);
  }
}

int readArr_string(char*** arr) {
  int out;
  scanf("%d", &out);
  *arr = malloc(out * sizeof(char*));
  readArr_strings(*arr, out);
  return out;
}

int qsort_asc_string(const void* a, const void* b) {
  return -1 * strcmp(*(char**) a, *(char**)b);
}

void printArr_string(char** arr, int dim) {
  for(int i = 0; i < dim; i++) {
    printf("%s\n", arr[i]);
  }
}

int main(int argc, char const *argv[]) {
  char** arr;
  int dim = readArr_string(&arr);
  qsort(arr, dim, sizeof(char*), qsort_asc_string);
  printArr_string(arr, dim);
  return 0;
}
