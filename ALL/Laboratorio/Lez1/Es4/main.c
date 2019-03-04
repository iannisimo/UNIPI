#include <stdio.h>
#include <stdlib.h>

void invert(int arr[], int dim);
void swap(int* a, int* b);

int main(int argc, char const *argv[]) {
  int dim;
  scanf("%d", &dim);
  int* arr = malloc(sizeof(int) * dim);
  for(int i = 0; i < dim; i++) {
    scanf("%d", &arr[i]);
  }
  invert(arr, dim);
  for(int i = 0; i < dim; i++) {
    printf("%d\n", arr[i]);
  }
  return 0;
}

void swap(int* a, int* b) {
  int tmp = *a;
  *a = *b;
  *b = tmp;
}

void invert(int arr[], int dim) {
  for(int i = 0; i < dim / 2; i++) {
    swap(&arr[i], &arr[dim - i - 1]);
  }
}
