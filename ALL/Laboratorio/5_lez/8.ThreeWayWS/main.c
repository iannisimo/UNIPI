
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

typedef struct {
  int x;
  int y;
} int_2d;

void swap(int*, int*);
int_2d distribuzione(int*, int, int, int);
void quicksort(int*, int, int);
int legge(int**, int*);

int main() {
  srand(time(NULL));
  int i, n, *A;

  if( legge(&A, &n)) return 1;

  srand(time(NULL));
  quicksort(A, 0, n-1);

  /* Stampa l'array ordinato */
  for( i = 0; i < n; i++)
    printf("%d ", A[i]);

  return 0;
}

void swap(int* a, int *b) {
  int t = *a;
  *a = *b;
  *b = t;
}

int_2d distribuzione(int a[], int sx, int px, int dx) {
  swap(&a[px], &a[dx]);
  px = dx;
  int i = sx - 1;
  for(int j = sx; j < dx; j++) {
    if(a[j] < a[px]) {
      swap(&a[++i], &a[j]);
    }
  }
  swap(&a[++i], &a[px]);
  return i;
}

void quicksort( int a[], int sx, int dx ) {
  int perno, pivot;
  if( sx < dx ) {
    pivot = sx + (rand() % (sx - dx));
    perno = distribuzione(a, sx, pivot, dx); // separa gli elementi minori di a[pivot]
					     // da quelli maggiori o uguali
    /* Ordina ricorsivamente le due metà */
    quicksort(a, sx, perno-1);
    quicksort(a, perno+1, dx);

  }
}

int legge(int **a, int *len) {

  int i;
  scanf("%d", len);
  if(*len <= 0) return 1;

  *a = (int *) malloc(*len * sizeof(int));
  if(*a == NULL) return 1;

  for( i = 0; i < *len; i++ )
	  scanf("%d", (*a)+i);

  return 0;

}
