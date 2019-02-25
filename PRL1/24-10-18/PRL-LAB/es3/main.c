#include <stdio.h>

#define ARR_DIM 10

int min_azzera(int a[], int dim) {
  int i;
  int min = a[0];
  for(i = 1; i < dim; i++) {
    if(a[i] < min) min = a[i];
    if(i%2) a[i] = 0;
  }
  return min;
}

int main(int argc, char const *argv[]) {
  int i;
  int arr[ARR_DIM];
  for(i = 0; i < ARR_DIM; i++) {
    scanf("%d", &arr[i]);
  }
  int min = min_azzera(arr, ARR_DIM);
  for(i = 0; i < ARR_DIM; i++) {
    printf("%d\n", arr[i]);
  }
  printf("%d\n", min);
  return 0;
}
