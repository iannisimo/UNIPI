#include <stdio.h>
#include <stdlib.h>

#define debug printf("Debug\n");

struct elemento {
  int info;
  struct elemento * next;
};
typedef struct elemento ElementoDiLista;
typedef ElementoDiLista* ListaDiElementi;

void printAll(ListaDiElementi head) {
  ListaDiElementi tmpHead = head;
  if(tmpHead != NULL) {
    printf("%d\n", tmpHead->info);
    printAll(tmpHead->next);
  }
}

ListaDiElementi headPush(ListaDiElementi oldHead, int v) {
  ListaDiElementi head = malloc(sizeof(ElementoDiLista));
  head->info = v;
  head->next = oldHead;
  return head;
}

ListaDiElementi goToTail(ListaDiElementi head) {
  ListaDiElementi el = head;
  if(el != NULL) {
    while(el->next != NULL) {
      el = el->next;
    }
  }
  return el;
}

void tailPush(ListaDiElementi head, int v) {
  ListaDiElementi oldTail = goToTail(head);
  ListaDiElementi tail = malloc(sizeof(ElementoDiLista));
  if(oldTail != NULL) {
    oldTail->next = tail;
  }
  tail->next = NULL;
  tail->info = v;
}

// ListaDiElementi scanUntil(ListaDiElementi el, int v) {
//   printf("<\n");
//   printAll(el);
//   ListaDiElementi tmpEl;
//   tmpEl = el;
//   ListaDiElementi out = NULL;
//   while(tmpEl->next != NULL && tmpEl->next->info != v) {
//     tmpEl = tmpEl->next;
//   }
//   if(tmpEl->next != NULL) {
//     out = tmpEl;
//   }
//   printf(">\n");
//   return out;
// }
//
// ListaDiElementi removeVal(ListaDiElementi head, int v) {
//   ListaDiElementi tmpHead = malloc(sizeof(ElementoDiLista));
//   tmpHead->next = head;
//   ListaDiElementi el = scanUntil(tmpHead, v);
//   while(el->next != NULL) {
//     el->next = el->next->next;
//     free(el->next);
//     el = scanUntil(el, v);
//   }
//   el = tmpHead->next;
//   free(tmpHead);
//   return el;
// }

ListaDiElementi removeVal(ListaDiElementi head, int v) {
  ListaDiElementi tmpHead;
  tmpHead->next = head;
  ListaDiElementi el = tmpHead;
  while(el->next != NULL) {
    if(el->next->info == v) {
      ListaDiElementi tmpEl = el->next;
      el->next = el->next->next;
      free(tmpEl);
    } else {
      printf("%d, ", el->info);
      el = el->next;
      printf("%d\n", el->info);
      sleep(1);
    }
  }
  return tmpHead;
}

int main(int argc, char const *argv[]) {
  int exit = 0;
  int tmpVal;
  ListaDiElementi head = NULL;
  while(!exit) {
    scanf("%d", &tmpVal);
    printf("%d:\n", tmpVal);
    if(tmpVal < 0) {
      head = removeVal(head, (-1)*tmpVal);
    } else if(tmpVal > 0) {
      if(tmpVal%2) {
        tailPush(head, tmpVal);
      } else {
        head = headPush(head, tmpVal);
      }
    } else {
      printAll(head);
      exit = 1;
    }
    printAll(head);
  }
  return 0;
}
