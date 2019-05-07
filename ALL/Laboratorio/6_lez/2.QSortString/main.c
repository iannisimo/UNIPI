#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char** readArrayString(int* dim);
void swapString(char* a, char* b);
void printArrayString(char** arr, int dim);

void QuickSortString(char**, int, int);
int  divideString(char**, int, int);

int main(int argc, char const *argv[]) {
  int dim;
  char** arr = readArrayString(&dim);
  QuickSortString(arr, 0, dim - 1);
  printArrayString(arr, dim);
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

void swapString(char* a, char* b) {
  char* t = malloc(100 * sizeof(char));
  strcpy(t, a);
  strcpy(a, b);
  strcpy(b, t);
  free(t);
}

void printArrayString(char** arr, int dim) {
  for(int i = 0; i < dim; i++) {
    printf("%s\n", arr[i]);
  }
}

void QuickSortString(char** arr, int sx, int dx) {
  if(sx < dx) {
    int px = divideString(arr, sx, dx);
    QuickSortString(arr, sx, px - 1);
    QuickSortString(arr, px + 1, dx);
  }
}

int divideString(char** arr, int sx, int dx) {
  int j = sx - 1;
  for(int i = sx; i < dx; i++) {
    if(strcmp(arr[i], arr[dx]) > 0) {
      j++;
      swapString(arr[i], arr[j]);
    }
  }
  j++;
  swapString(arr[dx], arr[j]);
  return j;
}
