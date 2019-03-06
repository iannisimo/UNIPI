#include <stdio.h>
#include <stdlib.h>

void readArrayString(char*** arr, int* dim);
// void insSortString(int* arr, int dim);
void printArrayString(char** arr, int dim);

int main(int argc, char const *argv[]) {
  char** arr;
  int  dim;
  readArrayString(&arr, &dim);
  // insSort(arr, dim);
  printArrayString(arr, dim);
  return 0;
}

void readArrayString(char*** arr, int* dim) {
  scanf("%d", dim);
  *arr = malloc(sizeof(char) * (*dim) * 100);
  for(int i = 0; i < (*dim); i++) {
    scanf("%s", *arr[i]);
  }
}

// void insSortString(int* arr, int dim) {
//   for(int i = 1; i < dim; i++) {
//     int key = arr[i];
//     int j = i-1;
//     while (j >= 0 && arr[j] > key) {
//       arr[j+1] = arr[j];
//       j--;
//     }
//     arr[j+1] = key;
//   }
// }
//
void printArrayString(char** arr, int dim) {
  for (int i = 0; i < dim; i++) {
    printf("%s\n", arr[i]);
  }
}
