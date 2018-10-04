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

int main(int argc, char const *argv[]) {
  int arr[] = {19, 523, 2, 231, 1, 999};
  printf("%d\n", maxIndex(arr, 6));
  return 0;
}
