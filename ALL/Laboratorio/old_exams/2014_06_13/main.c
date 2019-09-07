#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char name[101];
  int credits;
  int difficulty;
} exam;

exam* read_exams(int* k, int* n) {
  scanf("%d\n%d", k, n);
  exam* exams = malloc(*n * sizeof(exam));
  for(int i = 0; i < *n; i++) {
    scanf("%s\n%d\n%d", exams[i].name, &exams[i].credits, &exams[i].difficulty);
  }
  return exams;
}

int qsort_exams(const void* a, const void* b) {
  exam* c = (exam*) a;
  exam* d = (exam*) b;
  if(c->credits == d->credits) {
    if(c->difficulty == d->difficulty) {
      return strcmp(c->name, d->name);
    }
    return c->difficulty - d->difficulty;
  }
  return d->credits - c->credits;
}
int qsort_exam_names(const void* a, const void* b) {
  exam* c = (exam*) a;
  exam* d = (exam*) b;
  return strcmp(c->name, d->name);
}

exam* remove_undoable(exam* all_exams, int *n, int k) {
  exam* doable = malloc(*n * sizeof(exam));
  int index = 0;
  for(int i = 0; i < *n; i++) {
    if(k - all_exams[i].credits >= 0) {
      k -= all_exams[i].credits;
      doable[index] = all_exams[i];
      index++;
    }
  }
  *n = index;
  return doable;
}

void print_exams(exam* exams, int n) {
  for(int i = 0; i < n; i++) {
    printf("%s\n", exams[i].name);
  }
}

int main(int argc, char const *argv[]) {
  int k;
  int n;
  exam* exams = read_exams(&k, &n);
  qsort(exams, n, sizeof(exam), qsort_exams);
  exam* doable_exams = remove_undoable(exams, &n, k);
  qsort(doable_exams, n, sizeof(exam), qsort_exam_names);
  print_exams(doable_exams, n);
  return 0;
}

/*
30
6
AB
12
15
CD
6
10
EF
12
8
GH
12
9
LM
6
9
NP
9
14

*/
