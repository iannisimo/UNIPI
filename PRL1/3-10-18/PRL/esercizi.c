#include <stdio.h>

int member(int el, int a[], int dim) {
  int i = 0;                           // index var
  int found = 0;                       // output var
  while (i < dim && !found) {
    if (a[i] == el)  found = 1;
    else             i += 1;
  }
  return found;
}

int forEach(int a[], int dimA, int b[], int dimB) {
  int i = 0;
  int out = 1;
  while(i < dimA && out) {
    if (!member(a[i], b, dimB)) out = 0;
    else                        i++;
  }
  return out;
}

int count(int el, int a[], int dim) {
  int i;
  int out = 0;
  for(i = 0; i < dim; i++) {
    if(a[i] == el) out++;
  }
  return out;
}

int exactlyTwo(int a[], int dim) {
  int i = 0;
  int found = 0;
  while(i < dim && !found) {
    if(2 == count(a[i], a, dim)) found = 1;
    else                         i++;
  }
  return found;
}

int forEachExactlyTwo(int a[], int dim) {
  int i = 0;
  int notFound = 1;
  while(i < dim && notFound) {
    if(count(a[i], a, dim) != 2) notFound = 0;
    else                         i++;
  }
  return notFound;
}

int max(int a[], int dim, int *index) {
  int i;
  int out = a[0];
  *index = 0;
  for(i = 1; i < dim; i++) {
    if(a[i] > out) {
      out = a[i];
      *index = i;
    }
  }
  return out;
}

void rShift(int a[], int dim) {
  int i;
  int tmp = a[dim-1];
  for(i = dim; i > 0; i--) {
    a[i] = a[i-1]
  }
  a[0] = tmp;
}

void printArrayInt(int a[], int dim) {
  int i;
  printf("{");
  for(i = 0; i < dim; i++) {
    printf("%d, ", a[i]);
  }
  printf("}\n", );
}

int main(int argc, char const *argv[]) {
  int x[] = {0, 1, 2, 3, 4};
  int y[] = {4, 3, 2, 1, 0};
  printf("Universale: %d\n", forEach(x, 5, y, 5));
  int z[] = {1, 2, 2, 3, 2, 2};
  printf("Count: %d\n", count(2, z, 6));
  printf("Exactly Two: %d\n", exactlyTwo(z, 6));
  int za[] = {1, 1, 2, 2, 3, 4};
  printf("Each Exactly Two: %d\n", forEachExactlyTwo(za, 6));
  printArrayInt(za, 6);
  return 0;
}
