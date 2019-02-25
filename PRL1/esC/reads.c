#include  <stdio.h>
#include <stdlib.h>

#define vLst (*lst)

struct El {
  int         info;
  struct El*  next;
};

typedef struct El ElementoLista;


void printList(ElementoLista* lista) {
  printf("(");
  while(lista != NULL) {
    printf("%d ", lista -> info);
    lista = lista -> next;
  }
  printf(")\n");
}

void readListH(ElementoLista** lst) {
	int in;
	int ok = 1;
	ElementoLista* head = *lst;
	while(ok) {
		scanf("%d", &in);
		if(in >= 0) {
			ElementoLista* new 	= malloc(sizeof(ElementoLista));
			new -> info 		= in;
			new -> next 		= head;
			head 				= new;
		} else {
			ok = 0;
		}
	}
	*lst = head;
}

void readListT(ElementoLista** lst) {
	int in;
	int ok = 1;
	ElementoLista* tail = *lst;
	while(ok) {
		scanf("%d", &in);
		if(in >= 0) {
			ElementoLista* new 	= malloc(sizeof(ElementoLista));
			new -> info 		= in;
			new -> next			= NULL;
			if(tail == NULL) {
				*lst = new;
				tail = *lst;
			} else {
				tail -> next 	= new;
				tail 			= new;
			}
		} else {
			ok = 0;
		}
	}
}

void readListO(ElementoLista** lst) {
	int ok = 1;
	int in;
	ElementoLista* pointer;
	while(ok) {
		scanf("%d", &in);
		if(in >= 0) {
			ElementoLista* new 	= malloc(sizeof(ElementoLista));
			new -> info 		= in;
			if(*lst == NULL) {
				new -> next = NULL;
				*lst 		= new;
			} else {
				ElementoLista* prec = NULL;
				ElementoLista* curr = *lst;
				int found = 0;
				while(curr != NULL && !found) {
					if(curr -> info < in) {
						prec = curr;
						curr = curr -> next;
					} else {
						found = 1;
					}
				}
				if(prec == NULL) {
					new -> next 	= curr;
					*lst 			= new;
				} else {
					new -> next 	= curr;
					prec -> next 	= new;
				}
			}
		} else {
			ok = 0;		
		}
	}
}

int getEven(int val) {
	return !(val % 2);
}

void remEven(ElementoLista** lst) {
	ElementoLista* aux = *lst;
	if(aux != NULL) {
		if(getEven(aux -> info)) {
			*lst = aux -> next;
			free(aux);
			remEven(lst);
		} else {
			remEven(&((*lst) -> next));
		}
	}
}

void listSwap(ElementoLista** el) {
	ElementoLista* aux 	= (*el) -> next;
	(*el) -> next 		= aux -> next;
	aux -> next 		= *el;
	*el 				= aux;
}

int listLen(ElementoLista* lst) {
	int out = 0;
	if(lst != NULL) {
		out = 1 + listLen(lst -> next);
	}
	return out;
}

void listBS(ElementoLista** lst) {
	ElementoLista* last = NULL;
	ElementoLista* first = *lst;
	while(last != first) {
	if((*lst) != NULL) {
		ElementoLista** pointer = lst;
		int found = 0;
		while(!found) {
			printf("%d, %d -> ", (*pointer) -> info, (*pointer) -> next -> info);
			if((*pointer) -> info > (*pointer) -> next -> info) {
				listSwap(pointer);
			}
			printf("%d, %d | ", (*pointer) -> info, (*pointer) -> next -> info);
			printf("%d\n", (*pointer) -> next != last);
			printList(*lst);
			if((*pointer) != last) {
				pointer = &((*pointer) -> next);
			} else {
				last = *pointer;
				found = 1;
			}
		}
	}
	}
}

int main() {
	ElementoLista* list = NULL;
	readListT(&list);
	printList(list);
	listBS(&list);
	printList(list);
	remEven(&list);
	printList(list);
	
	return 0;
}
