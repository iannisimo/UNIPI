#include <stdio.h>

#define ROWS 4
#define COLS 3

void sum_matrix(int a[][COLS], int b[][COLS], int rows, int cols, int out[][COLS]) {
  int i, j;
  for(i = 0; i < rows; i++) {
    for(j = 0; j < cols; j++) {
      out[i][j] = a[i][j] + b[i][j];
    }
  }
}

int main(int argc, char const *argv[]) {
  int i, j;
  int mA[ROWS][COLS];
  int mB[ROWS][COLS];
  int mC[ROWS][COLS];
  for(i = 0; i < ROWS; i++) {
    for(j = 0; j < COLS; j++) {
      scanf("%d", &mA[i][j]);
    }
  }
  for(i = 0; i < ROWS; i++) {
    for(j = 0; j < COLS; j++) {
      scanf("%d", &mB[i][j]);
    }
  }
  sum_matrix(mA, mB, ROWS, COLS, mC);
  for(i = 0; i < ROWS; i++) {
    for(j = 0; j < COLS; j++) {
      printf("%d", mC[i][j]);
      if(i < ROWS-1 || j < COLS-1) printf(" ");
    }
    printf("\n");
  }
}
