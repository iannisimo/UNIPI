#include <stdio.h>

#define arrayDim 5

void readToArray(int tmp[], int dim) {
  int i;
  for(i = 0; i < dim; i++) {
    scanf("%d", &tmp[i]);
  }
}

void printArray(int tmp[], int dim) {
  int i;
  printf("[");
  for(i = 0; i < dim; i++) {
    if(i < dim-1) {
      printf("%d,", tmp[i]);
    } else {
      printf("%d", tmp[i]);
    }
  }
  printf("]\n");
}

int main(int argc, char const *argv[]) {
  int i;
  int a[arrayDim], b[arrayDim], c[arrayDim];
  readToArray(a, arrayDim);
  readToArray(b, arrayDim);
  printArray(a, arrayDim);
  printArray(b, arrayDim);
  for (i = 0; i < arrayDim; i++) {
    c[i] = a[i] + b[i];
  }
  printArray(c, arrayDim);
  return 0;
}
