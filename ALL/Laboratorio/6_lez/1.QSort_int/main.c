#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int readArr_int(int** arr) {
  int out;
  scanf("%d", &out);
  *arr = malloc(out * sizeof(int));
  for(int i = 0; i < out; i++) {
      scanf("%d", *arr + i);
  }
  return out;
}

int qsort_asc_int(const void* a, const void* b) {
  if(*(int*) a > *(int*) b) return 1;
  return -1;
}
int qsort_des_int(const void* a, const void* b) {
  if(*(int*) a < *(int*) b) return 1;
  return -1;
}
int qsort_eo_int(const void* a, const void* b) {
  if(*(int*) a%2) return 1;
  return -1;
}

int findOdd(int* arr, int dim) {
  for(int i = 0; i < dim; i++) {
    if(arr[i]%2) return i;
  }
  return dim;
}

void printArr_int(int* arr, int dim) {
  for(int i = 0; i < dim; i++) printf("%d\n", arr[i]);
}

int main(int argc, char const *argv[]) {
  int* arr;
  int dim = readArr_int(&arr);
  qsort(arr, dim, sizeof(int), qsort_eo_int);
  int oddI = findOdd(arr, dim);
  qsort(arr, oddI, sizeof(int), qsort_asc_int);
  qsort(arr + oddI, dim - oddI, sizeof(int), qsort_des_int);
  printArr_int(arr, dim);
  return 0;
}
