#include <stdio.h>

#define ARR_L 10

void reset    (int array[], int len);
void add      (int array[], int len, int val);
void printArr (int array[], int len);

int main(int argc, char const *argv[]) {
  int counter[ARR_L];
  reset(counter, ARR_L);
  int val;
  do {
    scanf("%d", &val);
    add(counter, ARR_L, val);
  } while (val != -1);
  printArr(counter, ARR_L);
  return 0;
}

void reset(int array[], int len) {
  for(int i = 0; i < len; i++) {
    array[i] = 0;
  }
}

void add (int array[], int len, int val) {
  if(val < len && val >= 0) {
    ++array[val];
  }
}

void printArr (int array[], int len) {
  for (int i = 0; i < len; i++) {
    printf("%d\n", array[i]);
  }
}
