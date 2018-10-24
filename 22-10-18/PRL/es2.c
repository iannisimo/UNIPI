#include <stdio.h>

int sumInt(int a[], int n, int k) {
  int i;
  int sum = 0;
  for(i = n-k; i <= n+k; i++) {
    sum += a[i];
  }
  return sum;
}

int min(int n, int m) {
  int out;
  if(n <= m) out = n;
  else out = m;
  return out;
}

int check(int a[], int dim, int k) {
  int nMax = min(k, dim-1-k);
  int i = 0;
  int found = 0;
  while(i < nMax && !found) {
    if(sum(a, i, k) == 0) {
      found = 1;
    } else {
      i++;
    }
  }
  return found;
}

int main(int argc, char const *argv[]) {
  int arr[] = {1, 4, 3, -4, 1};
  printf("%d\n", check(arr, 5, 2));
  return 0;
}
