//
//

#include <stdio.h>
#include <stdlib.h>

// controlla se la stringa e' di fatto un numero e se lo e' ritorna il numero
// altrimenti ritorna -1
long isNumber(const char* s) {
  char* e = NULL;
  long val = strtol(s, &e, 0);
  if (e != NULL && *e == (char)0) return val;
  return -1;
}

void print_usage(const char *programname) {
  printf("usage: %s -n <num> -m <num> -s <string> -h\n", programname);
}

int main(int argc, char *argv[]) {
  char *programname = argv[0];

  if (argc == 1) {
    printf("nessun argomento passato al programma\n");
    return 0;
  }

  char c;
  char foundn = 0, foundm=0, founds=0;
  long narg = -1, marg = -1;
  char *sarg = NULL;
  while(--argc > 0)
  if ((*++argv)[0] == '-') {
    while((c = *++argv[0]) == '-'); // alternativamente: while((c = argv[0][1]) == '-');
    switch(c) {
      case 'n': {
        foundn = 1;
        if (argv[0][1] == '\0') {
          ++argv, --argc;
          if (argv[0] == NULL || (narg = isNumber(argv[0]))==-1) {
            foundn = 0;
            printf("argomento n non valido\n");
          }
        } else
        if ((narg = isNumber(&argv[0][1]))==-1) {
          foundn = 0;
          printf("argomento n non valido\n");
        }
      } break;
      case 'm': {
        foundm = 1;
        if (argv[0][1] == '\0') {
          ++argv, --argc;
          if (argv[0] == NULL || (marg = isNumber(argv[0]))==-1) {
            foundm = 0;
            printf("argomento m non valido\n");
          }
        } else
        if ((marg = isNumber(&argv[0][1]))==-1) {
          foundm = 0;
          printf("argomento m non valido\n");
        }
      } break;
      case 's': {
        founds = 1;
        if (argv[0][1] == '\0') {
          ++argv, --argc;
          sarg = argv[0]; // attenzione questo e' rischioso, conviene usare strdup !
        } else sarg = &argv[0][1];
        if (sarg == NULL) {
          founds = 0;
          printf("argomento s non valido\n");
        }
      } break;
      case 'h': {
        print_usage(programname);
        return 0;
      } break;
      default :
      printf("argomento %c non riconosciuto\n", c);
    }
  }

  if (foundn)
  printf("-n: %ld\n", narg);
  if (foundm)
  printf("-m: %ld\n", marg);
  if (founds)
  printf("-s: %s\n", sarg);


  return 0;

}
