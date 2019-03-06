// Scrivere una funzione che, dato un array di interi e la sua lunghezza, lo
// ordini utilizzando l’algoritmo Selection Sort.
// Scrivere un programma che utilizzi la funzione per ordinare un array di
// N interi letti da input e stampi in output gli elementi dell’array ordinato.
// La prima riga dell’input contiene la dimensione N dell’array. Le righe
// successive contengono gli elementi dell’array, uno per riga.
// L’output contiene gli elementi dell’array ordinato, uno per riga.

#include <stdio.h>
#include <stdlib.h>

void readArray(int** arr, int* dim);
void printArray(int* arr, int dim);
void selSort(int* arr, int dim);

int main(int argc, char const *argv[]) {
  int* arr;
  int  dim;
  readArray(&arr, &dim);
  selSort(arr, dim);
  printArray(arr, dim);
  return 0;
}

void readArray(int** arr, int* dim) {
  scanf("%d\n", dim);
  *arr = malloc(sizeof(int) * (*dim));
  for(int i = 0; i < (*dim); i++) {
    scanf("%d", *arr + i);
  }
}

void selSort(int* arr, int dim) {
  for(int i = 0; i < dim-1; i++) {
    int min = i;
    for(int j = i; j < dim; j++) {
      if(arr[j] < arr[min]) min = j;
    }
    int tmp = arr[min];
    arr[min] = arr[i];
    arr[i] = tmp;
  }
}

void printArray(int* arr, int dim) {
  for (int i = 0; i < dim; i++) {
    printf("%d\n", arr[i]);
  }
}
