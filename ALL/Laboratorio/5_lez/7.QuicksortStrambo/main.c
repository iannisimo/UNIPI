#include <stdlib.h>
#include <stdio.h>
#include <time.h>

void swap(int* a, int *b);
int partEvenOdd(int a[], int sx, int dx);
int part(int a[], int sx, int px, int dx);
void quicksort( int a[], int sx, int dx );
int legge(int **a, int *len);

int main() {
  srand(time(NULL));
  int i, n, *A;

  if( legge(&A, &n)) return 1;

  int pivot = partEvenOdd(A, 0, n - 1);
  quicksort(A, 0, pivot);
  quicksort(A, pivot + 1, n - 1);

  /* Stampa l'array ordinato */
  for( i = 0; i < n; i++ )
    printf("%d ", A[i]);

  return 0;
}

void quicksort( int a[], int sx, int dx ) {

  int perno, pivot;
  if( sx < dx ) {
    pivot = sx + (rand() % (dx - sx));
    perno = part(a, sx, pivot, dx); // separa gli elementi minori di a[pivot]
					     // da quelli maggiori o uguali
    /* Ordina ricorsivamente le due metà */
    quicksort(a, sx, perno-1);
    quicksort(a, perno+1, dx);

  }
}

int partEvenOdd(int a[], int sx, int dx) {
  int i = sx - 1;
  for(int j = sx; j <= dx; j++) {
    if(!(a[j] % 2)) {  // <-- \

      swap(&a[++i], &a[j]);
    }
  }
  return i;
}


int part(int a[], int sx, int px, int dx) {
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

/* Lettura di un array di interi da input.
Il primo elemento è la lunghezza dell'array */
int legge(int **a, int *len) {

  int i;
  scanf("%d", len);
  if(*len <= 0) return 1;


/*
  !!!!!!!!!!!! D A  F A R E !!!!!!!!!!!!
  sx e dx sono le posizioni del primo e dell'ultimo elemento dell'array mentre
  px è la posizione dell'elemento perno.
  La funzione deve restituire la posizione del perno dopo che gli elementi sono
  stati partizionati.
*/
  *a = (int *) malloc(*len * sizeof(int));
  if(*a == NULL) return 1;

  for( i = 0; i < *len; i++ )
	  scanf("%d", (*a)+i);

  return 0;

}

void swap(int* a, int *b) {
  int t = *a;
  *a = *b;
  *b = t;
}
