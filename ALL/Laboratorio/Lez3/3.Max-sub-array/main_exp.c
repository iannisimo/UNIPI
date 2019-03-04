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
  if(sx == dx) {
    if(a[sx] > max) {
      return a[sx];
    } else {
      return max;
    }
  }
  int sum = a[sx];
  for(int i = sx + 1; i <= dx; i++) {
    sum = sum + a[i];
  }
  if(sum > max) max = sum;
  int left =  maxSubSum(a, sx, dx - 1, max);
  int right = maxSubSum(a, sx + 1, dx, max);
  if(left > right) return left;
  else             return right;
}
