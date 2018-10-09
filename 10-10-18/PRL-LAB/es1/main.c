#include <stdio.h>

#define arrayDim 10

int main(int argc, char const *argv[]) {
  int in[arrayDim];
  int i;
  for (i = 0; i < arrayDim; i++) {
    int tmp;
    // Richiedo i valori da tastiera e li metto in una variabile temporanea
    scanf("%d", &tmp);
    // Controllo se il valore letto e' pari o dispari
    if((tmp)%2) { // Dispari
      in[arrayDim - 1 - i] = tmp;     // Inserisco i valori nell'array in ordine inverso
    } else {      // Pari
      in[arrayDim - 1 - i] = tmp / 2; // Inserisco i valori dimezzati nell'array in ordine inverso
    }
  }
  // Ristampo tutti i valori a display
  for(i = 0; i < arrayDim; i++) {
    printf("%d\n", in[i]);
  }
  return 0;
}
