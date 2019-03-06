#include <stdio.h>

#define ARR_L 10

int* findVal(int a[], int len, int val);

int main(int argc, char const *argv[]) {
  int arr[ARR_L];
  int val;
  for(int i = 0; i < ARR_L; i++) {
    scanf("%d", &arr[i]);
  }
  scanf("%d", &val);
  int* pointer = findVal(arr, ARR_L, val);

if(pointer == NULL) {
  printf("non ");
}
printf("trovato\n");

  return 0;
}

int* findVal(int a[], int len, int val) {
  int* out = NULL;
  for(int i = 0; i < len; i++) {
    if(a[i] == val) out = &a[i];
  }
  return out;
}
