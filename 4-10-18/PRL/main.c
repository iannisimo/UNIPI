#include <stdio.h>

int maxIndex(int a[], int dim) {
  int i;
  int index = 0;
  int max = a[0];
  for(i = 1; i < dim; i++) {
    if(a[i] > max) {
      max = a[i];
      index = i;
    }
  }
  return index;
}

void swap(int *n, int *m) {
  int tmp = *n;
  *n = *m;
  *m = tmp;
}

void invert(int a[], int dim) {
  int i;
  for(i = 0; i < dim/2; i++) {
    swap(&a[i], &a[dim - 1 - i]);
  }
}

void bubble(int a[], int end) {
  int i;
  for(i = 0; i < end - 1; i++) {
    if(a[i] > a[i + 1]) swap(&a[i + 1], &a[i]);
  }
}

void bubbleSort(int a[], int dim) {
  int i;
  for (i = dim; i > 1; i--) {
    bubble(a, i);
  }
}

void printArrayInt(int a[], int dim) {
  int i;
  printf("{");
  for(i = 0; i < dim; i++) {
    printf("%d, ", a[i]);
  }
  printf("}\n", );
}

int main(int argc, char const *argv[]) {
  int arr[] = {19, 523, 2, 231, 1, 999};
  bubbleSort(arr, 6);
  printArrayInt(arr, 6);
  return 0;
}
