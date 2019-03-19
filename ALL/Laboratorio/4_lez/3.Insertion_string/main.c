#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char** readArrayString(int* dim);
void insSortString(char** arr, int dim);
void printArrayString(char** arr, int dim);

int main(int argc, char const *argv[]) {
  char** array;
  int    dim;
  array = readArrayString(&dim);
  insSortString(array, dim);
  printArrayString(array, dim);
  return 0;
}

char** readArrayString(int* dim) {
  scanf("%d", dim);
  char** arr = malloc(*dim * sizeof(char*));
  for(int i = 0; i < *dim; i++){
    arr[i] = malloc(100 * sizeof(char));
    scanf("%s", arr[i]);
  }
  return arr;
}

void insSortString(char** arr, int dim) {
  for(int i = 1; i < dim; i++) {
    char* key = arr[i];
    int j = i-1;
    while (j >= 0 && strcmp(arr[j], key) > 0) {
      arr[j+1] = arr[j];
      j--;
    }
    arr[j+1] = key;
  }
}

void printArrayString(char** arr, int dim) {
  for(int i = 0; i < dim; i++) {
    printf("%s\n", arr[i]);
  }
}
