#include <stdio.h>

#define ARR_DIM 3

int getMin(int a[], int dim) {
  int i;
  int min = a[i];
  for(i = 1; i < dim; i++) {
    if(a[i] < min) min = a[i];
  }
  return min;
}

int check(int a[], int dimA, int b[], int dimB) {
  int i = 0;
  int found = 0;
  int minB = getMin(b, dimB);
  while(i < dimA && !found) {
    if(a[i] < minB) found = 1;
    else i++;
  }
  return found;
}

int main(int argc, char const *argv[]) {
  int i;
  int a[ARR_DIM];
  int b[ARR_DIM];
  for(i = 0; i < ARR_DIM; i++) {
    scanf("%d", &a[i]);
  }
  for(i = 0; i < ARR_DIM; i++) {
    scanf("%d", &b[i]);
  }
  int found = check(a, ARR_DIM, b, ARR_DIM);
  if(found) printf("TRUE\n");
  else      printf("FALSE\n");
  return 0;
}
