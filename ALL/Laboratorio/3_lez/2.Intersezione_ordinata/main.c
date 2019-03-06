#include <stdio.h>
#include <stdlib.h>

void readArray(int** arr, int* dim);
int  arrayIntersections(int* arr_1, int dim_1, int* arr_2, int dim_2);
int  binaryMember(int *arr, int* sx, int dx, int val);

int main(int argc, char const *argv[]) {
  int* arr_a; int dim_a;
  int* arr_b; int dim_b;
  readArray(&arr_a, &dim_a);
  readArray(&arr_b, &dim_b);
  int out = arrayIntersections(arr_a, dim_a, arr_b, dim_b);
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

int  arrayIntersections(int* arr_i, int dim_i, int* arr_j, int dim_j) {
  int out = 0;
  int sx = 0;
  for(int i = 0; i < dim_i; i++) {
    out += binaryMember(arr_j, &sx, dim_j, arr_i[i]);
  }
  return out;
}

int  binaryMember(int *arr, int* sx, int dx, int val) {
  while(*sx <= dx) {
    int cx = (*sx + dx) / 2;
    if (val == arr[cx]) {
      return 1;
    }
    if(val < arr[cx]) {
      dx = cx - 1;
    } else {
      *sx = cx + 1;
    }
  }
  return 0;
}
