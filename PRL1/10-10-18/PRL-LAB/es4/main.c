#include <stdio.h>

#define arrayDim 10

int main(int argc, char const *argv[]) {
  int in[arrayDim];
  int i;
  for (i = 0; i < arrayDim; i++) {
    // Richiedo i valori da tastiera e li metto in una variabile temporanea
    scanf("%d", &in[i]);
  }
  for(i = 0; i < arrayDim; i++) {
    // Controllo le condizioni dettate dalla specifica
    if(
      (in[i] >= 0 && !(in[i]%2)) // Numero maggiore di zero e pari
      ||
      (i < arrayDim - 1 && in[i] < 0 && in[i+1] >= 0) // Numero negativo seguito da numero positivo (controllo di non essere nell'ultimo ciclo per evitare di controllare un valore fuori dall'array)
    ) {
      printf("%d\n", in[i]);
    }
  }
  return 0;
}
