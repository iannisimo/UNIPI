#include <stdio.h>
#include <stdlib.h>

typedef struct node {
  int info;
  struct node* next;
} ElementoDiLista;

void printAll(ElementoDiLista* head) {
  ElementoDiLista* tmpHead = head;
  if(tmpHead != NULL) {
    printf("%d\n", tmpHead->info);
    printAll(tmpHead->next);
  }
}

void newList(ElementoDiLista** l, int n) {
  int i;
  ElementoDiLista* curr;
  *l = NULL;
  if(n > 0) {
    *l = malloc(sizeof(ElementoDiLista));
    (*l)->info = 1;
    curr = *l;
    for(i = 2; i <= n; i++) {
      curr->next = malloc(sizeof(ElementoDiLista));
      curr = curr->next;
      curr->info = i;
    }
    curr->next = NULL;
  }
  // if(n > 0) {
  //   *l = malloc(sizeof(ElementoDiLista));
  //   curr = *l;
  //   for(i = 0; i <= n; i++) {
  //     curr->info = i;
  //     curr->next = malloc(sizeof(ElementoDiLista));
  //     curr = curr->next;
  //   }
  //   free(curr);
  // }
}

// Aggiungere ad una lista un elemento v dopo la prima occorrenza del valore el
// Se el non esiste la lista rimane invariata

// <
void addAfter(ElementoDiLista* l, int el, int v) {
  ElementoDiLista* curr = l;
  int found = 0;
  while(curr != NULL && !found) {
    if(curr->info == el) {
      found = 1;
    } else {
      curr = curr->next;
    }
  }
  if(found) {
    ElementoDiLista* newEl = malloc(sizeof(ElementoDiLista));
    newEl->info = v;
    newEl->next = curr->next;
    curr->next = newEl;
  }
}
// >

// Aggiungere un nuovo elemento contenente il parametro v ad una lista prima della prima occorrenza del valore el

// <
// void addBefore(ElementoDiLista** l, int el, int v) {
//   ElementoDiLista* tmpHead;
//   ElementoDiLista* curr = tmpHead;
//   curr->next = *l;
//   int found = 0;
//   while(curr->next != NULL && !found) {
//     if(curr->next->info == el) {
//       found = 1;
//     } else {
//       curr = curr->next;
//     }
//   }
//   if(found) {
//     ElementoDiLista* newEl = malloc(sizeof(ElementoDiLista));
//     newEl->info = v;
//     newEl->next = curr->next;
//     curr->next = newEl;
//   }
//   *l = tmpHead->next;
// }

// void addBefore(ElementoDiLista** l, int el, int v) {
//   int found = 0;
//   ElementoDiLista* curr = *l;
//   while(!found && curr->next != NULL) {
//     if(curr->next->info == el) {
//       found = 1;
//     } else {
//       curr = curr->next;
//     }
//   }
//   if(found) {
//     ElementoDiLista* newEl = malloc(sizeof(ElementoDiLista));
//     newEl->info = v;
//     newEl->next = curr->next;
//     curr->next = newEl;
//   }
// }

// void addBefore(ElementoDiLista** l, int el, int v) {
//   if(*l != NULL) {
//     if((*l)->info == el) {
//       ElementoDiLista* newEl = malloc(sizeof(ElementoDiLista));
//       newEl->info = v;
//       newEl->next = *l;
//       *l = newEl;
//     } else {
//       int found = 0;
//       ElementoDiLista* curr = *l;
//       while(!found && curr->next != NULL) {
//         if(curr->next->info == el) {
//           found = 1;
//         } else {
//           curr = curr->next;
//         }
//       }
//       if(found) {
//         ElementoDiLista* newEl = malloc(sizeof(ElementoDiLista));
//         newEl->info = v;
//         newEl->next = curr->next;
//         curr->next = newEl;
//       }
//     }
//   }
// }

void addBefore(ElementoDiLista** l, int el, int v) {
  ElementoDiLista* prec = NULL;
  ElementoDiLista* curr = *l;
  int found = 0;
  while (!found && curr != NULL) {
    if(curr->info == el) {
      found = 1;
    } else {
      prec = curr;
      curr = curr->next;
    }
  }
  if(found) {
    ElementoDiLista* newEl = malloc(sizeof(ElementoDiLista));
    newEl->info = v;
    if(prec == NULL) {
      newEl->next = *l;
      *l = newEl;
    } else {
      newEl->next = curr;
      prec->next = newEl;
    }
  }
}
// >

// rimuovere il primo elemento di una lista se esiste

// <
void removeFirst(ElementoDiLista** l) {
  if(*l != NULL) {
    ElementoDiLista* oldHead = *l;
    *l = oldHead->next;
    free(oldHead);
  }
}
// >

int main(int argc, char const *argv[]) {
  ElementoDiLista* head;
  newList(&head, 10);
  addAfter(head, 5, 11);
  addBefore(&head, 4, 0);
  removeFirst(&head);
  printAll(head);
  return 0;
}
