#include <stdio.h>

int main(int argc, char const *argv[]) {
  int in;
  int out = 1;
  // Leggo da tastira il valore da fattorializzare
  scanf("%d", &in);
  int i;
  // Inizzializzo un ciclo for che parte dal valore immesso e termina a '2'
  for (i = in; i > 1; i--) {
    // Moltiplico la variabile 'out' per la variabile i, la quale viene decrementata di 1 ad ogni ciclo
    out *= i;
  }
  // Stampo il fattoriale
  printf("%d\n", out);
  return 0;
}
