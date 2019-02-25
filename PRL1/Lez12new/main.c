#include <stdio.h>
#include <stdlib.h>

struct el {
  int info;
  struct el* next;
};

typedef struct el ElementoLista;

// readList() -------------------------------------------------------------------------------------
// Legge una sequenza di interi debolmete crescente                                               |
// Termina con la prima occorrenza di un numero che non rispetta l'ordinamento (non va inserito)  |
// Inserimento in coda                                                                            |
// ------------------------------------------------------------------------------------------------

void readList (ElementoLista** lst) {
  int a, b;

  *lst = malloc(sizeof(ElementoLista));

  ElementoLista* pointer  = (*lst);
  pointer->next           = NULL;

  scanf("%d", &b);
  pointer->info = b;
  a = b;

  scanf("%d", &b);
  while (b >= a) {
    pointer->next       = malloc(sizeof(ElementoLista));
    pointer->next->info = b;

    pointer       = pointer->next;
    pointer->next = NULL;

    a = b;
    scanf("%d", &b);
  }
}

// cancellaDuplicates() -------------------------
// Mantiene una sola occorrenza di ogni valore  |
// Ricorsiva                                    |
// ----------------------------------------------

void cancellaDuplicates(ElementoLista** lst) {
  ElementoLista* curr = *lst;
  if(curr->next != NULL) {
    if(curr->info == curr->next->info) {
      (*lst) = curr->next;
      cancellaDuplicates(lst);
    } else {
      cancellaDuplicates(&(curr->next));
    }
  }
}

// filterList(list1, list2) -------------------------------
// Elimina da list1 tutti gli elementi presenti in list2  |
// --------------------------------------------------------

int memberList(int val, ElementoLista* lst) {
  int found = 0;
  while(lst != NULL && !found) {
    if(lst->info == val) {
      found = 1;
    } else {
      lst = lst->next;
    }
  }
  return found;
}

void filterList (ElementoLista** lst1, ElementoLista* lst2) {
  ElementoLista* curr = *lst1;
  if(curr != NULL) {
    if(memberList(curr->info, lst2)) {
      *lst1 = curr->next;
      filterList(lst1, lst2);
    } else {
      filterList(&(curr->next), lst2);
    }
  }
}

// -------------------------------------

void printList(ElementoLista* list) {
  printf("(");
  while(list != NULL) {
    printf("%d ", list->info);
    list = list->next;
  }
  printf(")\n");
}

int main() {
  ElementoLista*  first_list;
  ElementoLista* second_list;

  // Read and print the first list
  readList(&first_list);

  printf("Prima lista\n");
  printList(first_list);

  // Remove duplicates from the first list
  cancellaDuplicates(&first_list);

  printf("Prima lista senza duplicati\n");
  printList(first_list);

  // Read and print the second list
  readList(&second_list);

  printf("Seconda lista\n");
  printList(second_list);

  // Remove duplicates from the second list
  cancellaDuplicates(&second_list);

  printf("Seconda lista senza duplicati\n");
  printList(second_list);

  // Filter the first list using the second list
  filterList(&first_list, second_list);

  printf("Lista filtrata\n");
  printList(first_list);

  return 0;
}
