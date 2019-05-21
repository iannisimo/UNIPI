#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char a[21];
  char aAnagramma[21];
} string;


string* readStrings(int*);
void sortChars(char*, char*);
void printGroups(string*, int);

int ordGroups(const void*, const void*);
int ordChars(const void*, const void*);


int main(int argc, char const *argv[]) {
  int n;
  string* strings = readStrings(&n);
  qsort(strings, n, sizeof(string), ordGroups);
  printGroups(strings, n);
  return 0;
}

string* readStrings(int* n) {
  scanf("%d", n);
  string* out;
  out = malloc(*n * sizeof(string));
  for(int i = 0; i < *n; i++) {
    scanf("%s", out[i].a);
    sortChars(out[i].aAnagramma, out[i].a);
  }
  return out;
}

int ordGroups(const void *a, const void *b) {
  string aa = *(string*) a;
  string bb = *(string*) b;

  if(aa.aAnagramma[0] == '\0') sortChars(aa.aAnagramma, aa.a);
  if(bb.aAnagramma[0] == '\0') sortChars(bb.aAnagramma, bb.a);
  int cmp = strcmp(aa.aAnagramma, bb.aAnagramma);
  if(!cmp) {
    return strcmp(aa.a, bb.a);
  }
  return cmp;
}

void printGroups(string* str, int n) {
  for(int i = 0; i < n-1; i++) {
    if(strcmp(str[i].aAnagramma, str[i+1].aAnagramma)) {
      printf("%s\n", str[i].a);
    } else {
      printf("%s ", str[i].a);
    }
  }
  printf("%s", str[n-1].a);
}


int ordChars(const void *a, const void *b) {
  //printf("%c - %c -> %d\n", *(char*) a, *(char*) b, *(char*) a - *(char*) b);
  return *(char*) a - *(char*) b;
}

void sortChars(char* dest, char* src) {
  strcpy(dest, src);
  qsort(dest, strlen(dest), sizeof(char), ordChars);
}
