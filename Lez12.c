/*cancellaDuplicates: Cancella da una lista tutti i duplicati. La procedura/funzione
deve essere ricorsiva.
Per esempio, supponendo che la lista sia (5, 8, 8, 15, 15, 15) la lista
modificata dovra' essere quella rappresentata in Figura 2.*/

#include <stdio.h>
#include <stdlib.h>

struct elemento{
	int info;
	struct elemento* next;
};

typedef struct elemento ElementoDiLista;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

void printlist(ElementoDiLista* lis){
  ElementoDiLista* curr = lis;
	while(curr != NULL){
		printf("%d\n",curr->info);
		curr = curr->next;
	}
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/*readList: Legge dallo standard input una sequenza di numeri interi
ordinati in maniera non descescente e termina automaticamente
l’acquisizione alla prima occorrenza di un numero che non rispetta
l’ordinamento (l’intero che viola l’ordinamento non va inserito nella lista).
Gli interi devono essere memorizzati, nell’ordine di acquisizione,
in una lista concatenata. Per esempio, supponendcd o che venga acquisita
la sequenza (5, 8, 15, 9) la lista di output dovr`a assere la seguente:*/

void push(ElementoDiLista* el, int val) {
	ElementoDiLista
}

void readList(ElementoDiLista** lst) {
	int val;
	int prec;
	scanf("%d", &val);
	*lst = malloc(sizeof(ElementoDiLista));
	
	while(val >= prec) {

		scanf("%d", &val);
	}
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/*cancellaDuplicates: Cancella da una lista tutti i duplicati. La procedura/funzione
deve essere ricorsiva.
Per esempio, supponendo che la lista sia (5, 8, 8, 15, 15, 15) la lista
modificata dovra' essere quella rappresentata in Figura 2.*/



// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/*filterList: date due liste list1 e list2 di lunghezza qualsiasi che non
contengono duplicati, la funzione/procedura elimina dalla lista list1
tutti gli elementi che sono presenti anche nella lista list2 . Esempio,
date due liste rispettivamente contententi gli interi (5, 8, 10, 15, 20, 24)
e (5, 8, 9, 10, 20, 21) la lista risultante dovr`a essere quella rappresentata
in Figura 3.*/

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

int main(){
	ElementoDiLista* first_list;
	ElementoDiLista* second_list;

	readList(first_list);

	printf("Prima lista\n");
	printlist(first_list);

	printf("Prima lista senza duplicati\n");\
	printlist(first_list);

	printf("Seconda lista\n");
	printlist(second_list);

	printf("Seconda lista senza duplicati\n");\
	printlist(second_list);

	printf("Lista filtrata\n");
	printlist(first_list);
	return 0;
}
