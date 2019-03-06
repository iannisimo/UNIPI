#include <stdio.h>
#include <stdlib.h>

int maxSubSum(int* arr, int dim);
void readArray(int** arr, int* dim);

int main(int argc, char const *argv[]) {
  int* arr;  int dim;
  readArray(&arr, &dim);
  int out = maxSubSum(arr, dim);
  printf("%d\n", out);
  return 0;
}

void readArray(int** arr, int* dim) {
  scanf("%d\n", dim);
  *arr = malloc(sizeof(int) * (*dim));
  for(int i = 0; i < (*dim); i++) {
    scanf("%d", *arr + i);
  }
}

int maxSubSum(int* arr, int dim) {
  int max = 0;
  int sum = 0;
  for (int i = 0; i < dim; i++) {
    if(sum > 0)   sum += arr[i];
    else          sum =  arr[i];

    if(sum > max) max = sum;
  }
  return max;
}
