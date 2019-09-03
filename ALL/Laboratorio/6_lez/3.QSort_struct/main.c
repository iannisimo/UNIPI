#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  int x;
  int y;
} vect2d;

int readArr_vect2d(vect2d** arr) {
  int out;
  scanf("%d", &out);
  *arr = malloc(sizeof(vect2d) * out);
  for(int i = 0; i < out; i++) {
    scanf("%d %d", &(*arr+i)->x, &(*arr+i)->y);
  }
  return out;
}

int qsort_vect2d(const void* a, const void* b) {
  vect2d* c = (vect2d*) a;
  vect2d* d = (vect2d*) b;
  if(c->x == d->x) {
    return d->y - c->y;
  }
  return c->x - d->x;
}

void printArr_vect2d(vect2d* arr, int dim) {
  for(int i = 0; i < dim; i++) {
    printf("%d %d\n", arr[i].x, arr[i].y);
  }
}

int main(int argc, char const *argv[]) {
  vect2d* arr;
  int dim = readArr_vect2d(&arr);
  qsort(arr, dim, sizeof(vect2d), qsort_vect2d);
  printArr_vect2d(arr, dim);
  return 0;
}
