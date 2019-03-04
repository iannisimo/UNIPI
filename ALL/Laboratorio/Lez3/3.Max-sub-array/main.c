#include <stdio.h>
#include <stdlib.h>

int maxSubSum(int* arr, int sx, int dx, int max);
void readArray(int** arr, int* dim);

int main(int argc, char const *argv[]) {
  int* arr;  int dim;
  readArray(&arr, &dim);
  int out = maxSubSum(arr, 0, dim - 1, -100);
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

int maxSubSum(int* a, int sx, int dx, int max) {

}
