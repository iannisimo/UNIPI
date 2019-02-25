#include <stdio.h>
// E i appartenente [0, dim) : a[i] < a[i+1]
// int lessThan (int a[], int dim) {
//   int i = start;
//   int found = 0;
//   while (i < dim - 1 && !found) {
//     if(a[i] < a[i+1]) found = 1;
//     else              i++;
//   }
//   return found;
// }
//
// int lessThanAll (int a[], int dim) {
//   int i = 0;
//   int lessThanEverything = 1;
//   while (i < dim-1 && lessThanEverything) {
//     if(a[i] < a[i+1]) i++;
//     else              lessThanEverything = 0;
//   }
// }

// E i appartenente [1, dim-1) : sum(j 0-->(i-1)) = sum(j (i+1)-->(dim-1))
// esempio arr[] = {3, 5, 2, 1, 1, 6}
int summ (int a[], int start, int end) {
  int sum = 0;
  int i;
  for (i = start; i <= end; i++) {
    sum += a[i];
  }
  return sum;
}

int findIndex (int a[], int dim) {
  int i = 0;
  int found = 0;
  while (i < dim-1 && !found) {
    if (summ(a, 0, i) == summ(a, i+1, dim-1)) {
      found = 1;
      printf("%d, %d > i : ", summ(a, 0, i), summ(a, i+1, dim-1));
    }
    else i++;
  }
  if (i == dim-1) i = -1;
  return i;
}

int main(int argc, char const *argv[]) {
  int arr[] = {4, 1, 2, 3, 4};
  printf("%d\n", findIndex(arr, 5));
  return 0;
}
