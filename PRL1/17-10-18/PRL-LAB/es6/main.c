#include <stdio.h>

#define ARR_DIM 5

int count_larger(int x, int arr[], int dim) {
  int i;
  int count = 0;
  for(i = 0; i < dim; i++) {
    if(arr[i] > x) {
      count++;
    }
  }
  return count;
}

int main(int argc, char const *argv[]) {
  int in;
  int inArr[ARR_DIM];
  scanf("%d", &in);
  int i;
  for(i = 0; i < ARR_DIM; i++) {
    scanf("%d", &inArr[i]);
  }
  printf("%d\n", count_larger(in, inArr, ARR_DIM));
  return 0;
}
