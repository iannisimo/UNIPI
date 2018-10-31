#include <stdio.h>

#define ARR_DIM 7

int even(int a) {
  return !(a%2);
}

void primoultimopari(int arr[], int dim, int *primaocc, int *ultimaocc) {
  int i = 0;
  *primaocc = -1;
  *ultimaocc = -1;
  while(i < dim && (-1 == *primaocc || -1 == *ultimaocc)) {
    if(even(arr[i])) *primaocc = i;
    if(even(arr[dim-1 - i])) *ultimaocc = dim-1 - i;
    i++;
  }
}

int main(int argc, char const *argv[]) {
  int i;
  int arr[ARR_DIM];
  int first, last;
  for(i = 0; i < ARR_DIM; i++) {
    scanf("%d", &arr[i]);
  }
  primoultimopari(arr, ARR_DIM, &first, &last);
  printf("%d %d\n", first, last);
  return 0;
}
