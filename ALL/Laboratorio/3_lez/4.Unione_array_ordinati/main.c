#include <stdio.h>
#include <stdlib.h>

void readArray(int** arr, int* dim);
int* arrayUnion(int* arr_i, int dim_i, int* arr_j, int dim_j);
void printArray(int* arr, int dim);

int main(int argc, char const *argv[]) {
  int* arr_a; int dim_a;
  int* arr_b; int dim_b;
  readArray(&arr_a, &dim_a);
  readArray(&arr_b, &dim_b);
  int* out = arrayUnion(arr_a, dim_a, arr_b, dim_b);
  printArray(out, dim_a + dim_b);
  return 0;
}

void readArray(int** arr, int* dim) {
  scanf("%d\n", dim);
  *arr = malloc(sizeof(int) * (*dim));
  for(int i = 0; i < (*dim); i++) {
    scanf("%d", *arr + i);
  }
}

int* arrayUnion(int* arr_i, int dim_i, int* arr_j, int dim_j) {
  int i = 0;
  int j = 0;
  int* out = malloc(sizeof(int) * (dim_i + dim_j));
  for(int k = 0; k < dim_i + dim_j) {
    out[k] = arr_i[i] < arr_j[j] ? arr_i[i] : arr_j[j];
    if(i < dim_i - 1) {
      i++;
    } else if()

  }
  return out;
}

void printArray(int* arr, int dim) {
  for (int i = 0; i < dim; i++) {
    printf("%d\n", arr[i]);
  }
}
