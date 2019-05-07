#include <stdio.h>
#include <stdlib.h>

void readArray(int**, int*);
void swap(int*, int*);
void printArray(int*, int);

void QuickSortEO(int*, int);
int  divideEO(int*, int, int);

void quickSortInc(int*, int, int);
int  divideInc(int*, int, int);

void quickSortDec(int*, int, int);
int  divideDec(int*, int, int);

int main(int argc, char const *argv[]) {
  int* arr;
  int dim;
  readArray(&arr, &dim);
  QuickSortEO(arr, dim);
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

void QuickSortEO(int* arr, int dim) {
  if(dim > 0) {
    int px = divideEO(arr, 0, dim - 1);
    quickSortInc(arr, 0, px);
    quickSortDec(arr, px + 1, dim - 1);
  }
}

int divideEO(int* arr, int sx, int dx) {
  int j = sx - 1;
  for(int i = sx; i <= dx; i++) {
    if(!(arr[i]%2)) {
      j++;
      swap(&arr[i], &arr[j]);
    }
  }
  return j;
}

void quickSortInc(int* arr, int sx, int dx) {
  if(sx < dx) {
    int px = divideInc(arr, sx, dx);
    quickSortInc(arr, sx, px - 1);  // <-- Check
    quickSortInc(arr, px + 1, dx);  // <-- Check
  }
}

int divideInc(int* arr, int sx, int dx) {
  int j = sx - 1;
  for(int i = sx; i < dx; i++) {
    if(arr[i] < arr[dx]) {
      j++;
      swap(&arr[i], &arr[j]);
    }
  }
  j++;
  swap(&arr[j], &arr[dx]);
  return j;
}

void quickSortDec(int* arr, int sx, int dx) {
  if(sx < dx) {
    int px = divideDec(arr, sx, dx);
    quickSortDec(arr, sx, px - 1);  // <-- Check
    quickSortDec(arr, px + 1, dx);  // <-- Check
  }
}

int divideDec(int* arr, int sx, int dx) {
  int j = sx - 1;
  for(int i = sx; i < dx; i++) {
    if(arr[i] > arr[dx]) {
      j++;
      swap(&arr[i], &arr[j]);
    }
  }
  j++;
  swap(&arr[j], &arr[dx]);
  return j;
}

void swap(int* a, int *b) {
  int t = *a;
  *a = *b;
  *b = t;
}

void printArray(int* arr, int dim) {
  for (int i = 0; i < dim; i++) {
    printf("%d\n", arr[i]);
  }
}
