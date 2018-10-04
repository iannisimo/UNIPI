#include <stdio.h>


int member (int el, int a[], int dim) {
  int i = 0;                           // index var
  int found = 0;                       // output var
  while (i < dim && !found) {
    if (a[i] == el)  found = 1;
    else             i += 1;
  }
  return found;
}

int main () {
  int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
  printf("%d\n", member(54, arr, 10));
  return 0;
}
