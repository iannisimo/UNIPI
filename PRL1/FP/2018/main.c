#include  <stdio.h>
#include <stdlib.h>

struct El {
  int         info;
  struct El*  next;
};

typedef struct El ElementoLista;

ElementoLista*  readList();
int             OddEven(ElementoLista* lista);
ElementoLista*  Equality(ElementoLista* lista);

void printList(ElementoLista* lista) {
  printf("(");
  while(lista != NULL) {
    printf("%d ", lista -> info);
    lista = lista -> next;
  }
  printf(")\n");
}

int main() {
  ElementoLista* list = NULL;
  int res = -1;

  list = readList();

  printf("La lista letta e':\n");
  printList(list);

  res = OddEven(list);

  printf("OddEven restituisce: %d\n", res);

  printf("La lista troncata e':\n");
  printList(Equality(list));

  return 0;
}

// Leggere da stdInput una sequenza di interi alternati tra pari e dispari
// fino a che non viene letto un valore che non rispetta l'ordinamento
// Inserirli in una lista con inserimento in testa
// (Non inserire l'ultimo)

int getEven(int val) {
  return !(val%2);
}

ElementoLista* readList() {
  ElementoLista*  lst = malloc(sizeof(ElementoLista));
  int             in;
  int             even;
  scanf("%d", &in);
  even        = getEven(in);
  lst -> info = in;
  lst -> next = NULL;
  scanf("%d", &in);
  while(getEven(in) != even) {
    ElementoLista* aux = lst;
    lst         =  malloc(sizeof(ElementoLista));
    lst -> info = in;
    lst -> next = aux;
    even        = getEven(in);
    scanf("%d", &in);
  }
  return lst;
}

// ElementoLista* readListAux(ElementoLista* lst, int even) {
//   ElementoLista*  out = lst;
//   int             in;
//   scanf("%d", &in);
//   if(getEven(in) != even || even == 2) {
//     ElementoLista* head = malloc(sizeof(ElementoLista));
//     head -> info = in;
//     head -> next = lst;
//     out = readListAux(head, getEven(in));
//   }
//   return out;
// }
//
//
// ElementoLista* readList() {
//   return readListAux(NULL, 2);
// }


// Questa funzione prende una lista di interi e restituisce 1 se
// essa contiene lo stesso numero di elementi pari ed elementi dispari.
// Restituisce 0 altrimenti.

int OddEven(ElementoLista* lista) {
  int out = 1;
  while(lista != NULL) {
    out   = !out;
    lista = lista -> next;
  }
  return out;
}


// Data una lista di interi, Equality deve cancellare dalla
// coda della lista il minimo numero (che pu`o anche essere = 0) di
// elementi affinch´e la lista risultante contenga tanti numeri pari quanti
// numeri dispari.

ElementoLista* Equality(ElementoLista* lista) {
  ElementoLista* head = lista;
  int out = 1;
  while(lista -> next -> next != NULL) {
    out   = !out;
    lista = lista -> next;
  }
  if(out == 0) {
    ElementoLista* aux = lista -> next;
    lista -> next = NULL;
    free(aux);
  }
  return head;
}
