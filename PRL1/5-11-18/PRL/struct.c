// Linked list

#include <stdlib.h>

typedef struct node {
  int info;
  struct node* next;
} listElement;

int main(int argc, char const *argv[]) {
  listElement* l = malloc(sizeof(listElement));
  // (*l).info = 1;
  // (*l).next = malloc(sizeof(listElement));
  // (*(*l).next).info = 2;
  // (*(*l).next).next = NULL;

  l->info = 1;
  l->next = malloc(sizeof(listElement));
  l->next->info = 2;
  l->next->next = NULL;

  return 0;
}
