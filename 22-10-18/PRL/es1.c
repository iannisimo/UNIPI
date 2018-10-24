#include <stdio.h>

int conta(int el, int a[], int dim) {
  int i;
  int out = 0;
  for(i = 0; i < dim; i++) {
    if(a[i] == el) out++;
  }
  return out;
}

int member(int el, int a[], int dim) {
  return conta(el, a, dim) != 0;
}

int check(int a[], int dima, int b[], int dimb) {
  int i = 0;
  int ok = 1;
  int found = 0;
  while(i < dima && ok) {
    if(conta(a[i], a, dima) != conta(a[i], b, dimb)) {
      ok = 0;
    } else {
      i++;
    }
  }
  if(ok) {
    i = 0;
    while(i < dimb && !found) {
      if(!member(b[i], a, dima)) {
        found = 1;
      } else {
        i++;
      }
    }
  }
  return ok && found;
}

int main(int argc, char const *argv[]) {

  return 0;
}
