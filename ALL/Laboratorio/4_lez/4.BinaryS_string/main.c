#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int loop(char** arr, int dim);
char** readArrayString(int* dim);
int binarySearchString(char* el, char** arr, int dx);

int main(int argc, char const *argv[]) {
  char** array;
  int    dim;
  int    ok = 1;
  array = readArrayString(&dim);
  while(ok) {
      ok = loop(array, dim);
  }
  return 0;
}

int loop(char** array, int dim) {
    int command;
    char search[100];
    scanf("%d", &command);
    if(command) {
        scanf("%s", search);
        printf("%d\n", binarySearchString(search, array, dim));
    }
    return command;
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

int binarySearchString(char* el, char** arr, int dx) {
  int sx = 0;
  while(sx <= dx) {
    int cx = (sx + dx) / 2;
    int cmp = strcmp(el, arr[cx]);
    if (!cmp) {
      return cx;
    }
    if(cmp < 0) {
      dx = cx - 1;
    } else {
      sx = cx + 1;
    }
  }
  return -1;
}
