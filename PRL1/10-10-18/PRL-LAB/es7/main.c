#include <stdio.h>

int main(int argc, char const *argv[]) {
  int h, l;
  int i, j;
  // Richiedo da tastiera i valori di altezza e larghezza
  scanf("%d\n%d", &h, &l);
  // Creo un ciclo che verra' eseguito un numero h di volte
  for (i = 0; i < h; i++) {
    // Creo un ciclo annidato che viene eseguito un numero l di volte
    for (j = 0; j < l; j++) {
      // Se sono nella prima o l'ultima iterazione di i stampo soltanto '*' per creare il bordo superiore ed inferiore
      if(i == 0 || i == h-1) {
        printf("*");
      } else {
        if (j == 0 || j == l-1) { // Se sono nella prima o l'ultima itarazione di j stampo '*' per creare i bordi laterali
          printf("*");
        } else {                  // Senno' stampo ' '
          printf(" ");
        }
      }
    }
    // Vado a capo alla fine di ogni iterazione di i
    printf("\n");
  }
  return 0;
}
