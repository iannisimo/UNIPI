#include <stdio.h>

int main(int argc, char const *argv[]) {
  unsigned int in;
  int i;
  // Richiedo un valore da tastiera
  scanf("%u", &in);
  // Eseguo un ciclo che parte dal valore immesso ed arriva a 0 decrescendo di 2 ad ogni iterazione
  for(i = in; i > 0; i -= 2) {
    int j;
    // Creo un ciclo annidato che stampa un numero di '*' pari al valore della variabile di controllo del ciclo padre
    for (j = 0; j < i; j++) {
      printf("*");
    }
    // Vado a capo
    printf("\n");
  }
  return 0;
}
