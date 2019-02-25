#include <stdio.h>
#include <stdlib.h>

struct El {

	int 		info;
	struct El*  next;
};

typedef struct El elementoLista;

void printList(elementoLista* list) {
	printf("(");
	while(list != NULL) {
		printf("%d ", list -> info);
		list = list -> next;
	}
	printf(")\n");
}

int readList(elementoLista** lst) {
	int out 			= 0;
	int in 				= 1;
	int last 			= 0;
	int ok 				= 1;
	elementoLista* curr;
	while(ok) {
		scanf("%d", &in);
		printf("%d, %d\n", in, last);
		if(*lst == NULL) {
			elementoLista* new 	= malloc(sizeof(elementoLista));
			new -> info 		= in;
			new -> next 		= NULL;
			*lst 				= new;
			curr 				= *lst;
			out++;
		} else if(in > last) {
			elementoLista* new	= malloc(sizeof(elementoLista));
			new  -> info 		= in;
			new  -> next 		= NULL;
			curr -> next		= new;
			curr				= curr -> next;
			out++;
		} else {
			ok = 0;
		}
		last = in;
	}
	printf("%d\n", out);
	return out;
}

int main() {
	elementoLista* firstList = NULL;
	elementoLista* secondList = NULL;
	int size1 = 0;
	int size2 = 0;
	size1 = readList(&firstList);
	size2 = readList(&secondList);
	
	printf("Prima lista (%d elementi)\n", size1);
	printList(firstList);
	
	printf("Seconda lista (%d elementi)\n", size2);
	printList(secondList);
	/*
	filterLists(&firstList, secondList);
	
	printf("Lista filtrata\n");
	printList(firstList);
	*/
	return 0;
}


	
