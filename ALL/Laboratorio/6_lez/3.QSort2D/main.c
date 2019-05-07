#include <stdio.h>
#include <stdlib.h>

typedef struct {
  int x;
  int y;
} vec2d;

vec2d** read2DArray(int*);
void swap2D(vec2d*, vec2d*);
void print2DArray(vec2d**, int);

void QuickSort2D(vec2d**, int, int);
int  divide2D(vec2d**, int, int);

int main(int argc, char const *argv[]) {
  int dim;
  vec2d** arr = read2DArray(&dim);
  // QuickSort2D(arr, 0, dim - 1);
  print2DArray(arr, dim);
  return 0;
}

vec2d** read2DArray(int* dim) {
  vec2d** arr = malloc(*dim * sizeof(vec2d));
  scanf("%d", dim);
  for(int i = 0; i < *dim; i++) {
    int x, y;
    scanf("%d %d", &x, &y);
    
  }
  return arr;
}

void print2DArray(vec2d** arr, int dim) {
  for(int i = 0; i < dim; i++) {
    printf("%d %d\n", arr[i] -> x, arr[i] -> y);
  }
}
