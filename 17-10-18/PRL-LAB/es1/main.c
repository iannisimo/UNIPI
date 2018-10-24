#include <stdio.h>

#define arrDim 10

int positive(int n) {
  int out = 0;
  if (n >= 0) {
    out = 1;
  }
  return out;
}

float media(int arr[], int dim) {
  int i;
  int sum = 0;
  int count = 0;
  for(i = 0; i < dim; i++) {
    if(positive(arr[i]) == positive(arr[dim-1]) && arr[i] != 0) {
      sum += arr[i];
      count++;
    }
  }
  return (float) sum / count;
}

int main() {
  int i;
  int in[arrDim];
  for (i = 0; i < arrDim; i++) {
    scanf("%d", &in[i]);
  }
  printf("%.2f\n", media(in, arrDim));
  return 0;
}
