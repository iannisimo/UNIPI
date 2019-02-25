#include <stdio.h>

#define ROWS_A 4
#define COLS_A 2
#define ROWS_B 2
#define COLS_B 3

#define ROWS_C ROWS_A
#define COLS_C COLS_B

void mul_matrix(int a[][COLS_A], int b[][COLS_B], int rowsA, int colsA, int rowsB, int colsB, int out[][COLS_C]) {
  int i, j, h;
  for(j = 0; j < colsB; j++) {
    int tmpMul[rowsB];
    for(i = 0; i < rowsB; i++) {
      tmpMul[i] = b[i][j];
    }
    for(i = 0; i < rowsA; i++) {
      out[i][j] = 0;
      for(h = 0; h < colsA; h++) {
        out[i][j] += a[i][h] * b[h][j];
      }
    }
  }
}

int main(int argc, char const *argv[]) {
  int i, j;
  int mA[ROWS_A][COLS_A];
  int mB[ROWS_B][COLS_B];
  int mC[ROWS_C][COLS_C];
  for(i = 0; i < ROWS_A; i++) {
    for(j = 0; j < COLS_A; j++) {
      scanf("%d", &mA[i][j]);
    }
  }
  for(i = 0; i < ROWS_B; i++) {
    for(j = 0; j < COLS_B; j++) {
      scanf("%d", &mB[i][j]);
    }
  }
  mul_matrix(mA, mB, ROWS_A, COLS_A, ROWS_B, COLS_B, mC);

  for(i = 0; i < ROWS_C; i++) {
    for(j = 0; j < COLS_C; j++) {
      printf("%d ", mC[i][j]);
    }
    printf("\n");
  }
  return 0;
}
