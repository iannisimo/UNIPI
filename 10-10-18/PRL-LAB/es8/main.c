#include <stdio.h>

int main(int argc, char const *argv[]) {
  int in;
  int i;
  // Richiedo un valore da tastiera
  scanf("%d", &in);
  // Creo un ciclo da 10 iterazioni
  for(i = 1; i <= 10; i++) {
    printf("%d\n", in * i); // stampo il valore letto da tastiera moltiplicato per la variabile di controllo del ciclo
  }
  return 0;
}

// Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
