
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>

char** readArrayString(int* dim);
void swapString(char* a, char* b);
int partString(char** a, int sx, int px, int dx);
void quicksortString(char** a, int sx, int dx);
void printArrayString(char** arr, int dim);




int main() {
  char** arr;
  int dim;
  srand(time(NULL));
  int i;
  arr = readArrayString(&dim);

  quicksortString(arr, 0, dim-1);

  printArrayString(arr, dim);

  return 0;
}

char** readArrayString(int* dim) {
  scanf("%d", dim);
  char** arr = malloc(*dim * sizeof(char*));
  for(int i = 0; i < *dim; i++){
    arr[i] = malloc(100 * sizeof(char));
    scanf("%s", arr[i]);
  }
  return arr;
}

int partString(char** a, int sx, int px, int dx) {
  swapString(a[px], a[dx]);
  px = dx;
  int i = sx - 1;
  for(int j = sx; j < dx; j++) {
    if(strcmp(a[j], a[px]) < 0) {
      swapString(a[++i], a[j]);
    }
  }
  swapString(a[++i], a[px]);
  return i;
}

void quicksortString(char** a, int sx, int dx ) {

  int perno, pivot;
  if( sx < dx ) {
    pivot = sx + (rand() % (sx - dx));
    perno = partString(a, sx, pivot, dx); // separa gli elementi minori di a[pivot]
					     // da quelli maggiori o uguali
    /* Ordina ricorsivamente le due metà */
    quicksortString(a, sx, perno-1);
    quicksortString(a, perno+1, dx);

  }

}

void swapString(char* a, char* b) {
  char* t = malloc(100 * sizeof(char));
  strcpy(t, a);
  strcpy(a, b);
  strcpy(b, t);
  free(t);
}

void printArrayString(char** arr, int dim) {
  for(int i = 0; i < dim; i++) {
    printf("%s\n", arr[i]);
  }
}
