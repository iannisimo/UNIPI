#include <stdio.h>

int check(int a[], int dim) {
  int i = 1;
  int j = 0;
  int k;
  int ok = 1;
  while(i < dim && ok) {
    int found = 0;
    int sum = 0;
    for(k = 0; k < i; k++) {
      sum += a[k];
    }
    while(j < i && !found) {
      if(a[i] == sum) {
        found = 1;
      } else {
        j++;
        sum -= a[j];
      }
    }
    ok = found;
    i++;
  }
  return ok;
}

int main(int argc, char const *argv[]) {
  int a[] = {1, 1, 1, 3};
  printf("%d\n", check(a, 4));
  return 0;
}
