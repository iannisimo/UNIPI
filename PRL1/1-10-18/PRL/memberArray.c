#include <stdio.h>

int member (int el, int a[], int start, int end) {
  int i = start;                           // index var
  int found = 0;                       // output var
  while (i < end && !found) {
    if (a[i] == el)  found = 1;
    else             i += 1;
    printf("%d, %d, %d\n", i-1, el, a[i-1]);±
  }
  return found;
}

int allDifferent (int a[], int dim) {
  int i = 0;
  int different = 1;
  while (i < dim-1 && different) {
    different = !member (a[i], a, i+1, dim);  // Se viene trovato il valore in a[i] nell'array la funzione si ferma
    printf("    %d, %d, %d\n", i, a[i], different);
    i++;
  }
  return different;
}

int main () {
  int arr[] = {1, 2, 3, 4, 5};
  printf("%d\n", allDifferent(arr, 5));
}
