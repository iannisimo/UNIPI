#include <stdio.h>
#include <stdlib.h>

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;

void cancFirst(ElementoDiLista** l, int v) {
  ElementoDiLista* prec = NULL;
  ElementoDiLista* curr = *l;
  int found = 0;
  while(curr != NULL && !found) {
    if(curr->info == v) {
      found = 1;
    } else {
      prec = curr;
      curr = curr->next;
    }
  }
  if(found) {
    if(prec == NULL) {
      *l = curr->next;
    } else {
      prec->next = curr->next;
    }
    free(curr);
  }
}

void cancAll(ElementoDiLista** l, int v) {
  ElementoDiLista* prec = NULL;
  ElementoDiLista* curr = *l;
  while(curr->NULL) {
    if(curr->info == v) {
      if(prec == NULL) {
        *l = curr->next;
        free(curr);
        curr = *l;
      } else {
        prec->next = curr->next;
        free(curr);
        curr = prec->next;
      }
    } else {
      prec = curr;
      curr = curr->next;
    }
  }
}

// Scrivere una procedura che inverte l'ordine degli elementi di una lista

// <
void reverse(ElementoDiLista** l) {
  ElementoDiLista* lNew = NULL;
  ElementoDiLista* lOld = *l;
  while(lOld != NULL) {
    ElementoDiLista* aus = lOld;
    lOld = lOld->next;
    aus->next = lNew;
    lNew = aus;
  }
  *l = lNew;
}
