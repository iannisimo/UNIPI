#include <stdio.h>

#define arrayDim 10

int main(int argc, char const *argv[]) {
  int in[arrayDim];
  int i;
  for (i = 0; i < arrayDim; i++) {
    // Richiedo i valori da tastiera e li metto in una variabile temporanea
    scanf("%d", &in[i]);
  }
  int found = 0;
  i = 1;
  // Cerco la prima occorrenza che soddisfa la richiesta
  while (i < arrayDim - 1 && !found) {
    if (in[i + 1] - in[i - 1] == in[i]) found = 1; // Se la specifica e' soddisfatta cambio il valore di found per uscire dal ciclo
    else i++;                                      // Senno' incremento i
  }
  if(i >= arrayDim - 1) i = -1; // Se il valore di i eccede la dimensione dell'array significa che non e' stato trovato alcun valore che soddisfa la specifica
  printf("%d\n", i); // stampo il valore di i
  return 0;
}
