#include <stdio.h>
#include <stdlib.h>

char** readArrayString(int* dim);

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

void swap(int* a, int* b) {
  int tmp = *a;
  *b = *a;
  *a = tmp;
}

int part(int arr[], int lo, int hi) {
  int pivot = arr[hi];
  int i = lo;
  for(int j = lo; j <= hi - 1; j++) {
    if(arr[j] < pivot) {
      swap(&arr[i], &arr[j]);
      i++;
    }
  }
  swap(&arr[i], &arr[hi]);
}

void quickSort(int arr[], int lo, int hi) {
  if(lo < hi) {
    int p = part(arr, lo, hi);
    quickSort(arr, lo, p - 1);
    quickSort(arr, p + 1, hi);
  }
}

int main(int argc, char const *argv[]) {
  int** arr;
  int  dim;
  arr = readArrayString(&dim);
  quickSort(arr, 0, dim - 1);
  printArray(arr, dim);
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
