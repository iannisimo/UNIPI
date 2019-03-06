#include <stdio.h>
#include <stdlib.h>

void readArray(int** arr, int* dim);
void printArray(int* arr, int dim);
void insSort(int* arr, int dim);

int main(int argc, char const *argv[]) {
  int* arr;
  int  dim;
  readArray(&arr, &dim);
  insSort(arr, dim);
  printArray(arr, dim);
  return 0;
}

void readArray(int** arr, int* dim) {
  scanf("%d\n", dim);
  *arr = malloc(sizeof(int) * (*dim));
  for(int i = 0; i < (*dim); i++) {
    scanf("%d", *arr + i);
  }
}

void insSort(int* arr, int dim) {
  for(int i = 1; i < dim; i++) {
    int key = arr[i];
    int j = i-1;
    while (j >= 0 && arr[j] > key) {
      arr[j+1] = arr[j];
      j--;
    }
    arr[j+1] = key;
  }
}

void printArray(int* arr, int dim) {
  for (int i = 0; i < dim; i++) {
    printf("%d\n", arr[i]);
  }
}
