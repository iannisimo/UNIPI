#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char value[100];
  int frequency;
} string_f;

void readArr_strings(char** arr, int dim) {
  for(int i = 0; i < dim; i++) {
    arr[i] = malloc(100 * sizeof(char));
    scanf("%s", arr[i]);
  }
}

void readArr(char*** arr, int* n, int* k) {
  scanf("%d\n%d", n, k);
  *arr = malloc(*n * sizeof(char*));
  readArr_strings(*arr, *n);
}

int qsort_asc_string(const void* a, const void* b) {
  return -1 * strcmp(*(char**) a, *(char**)b);
}
int qsort_dis_string_f_freq(const void* a, const void* b) {
  string_f* c = (string_f*) a;
  string_f* d = (string_f*) b;
  return d->frequency - c->frequency;
}
int qsort_des_string_f_val(const void* a, const void* b) {
  string_f* c = (string_f*) a;
  string_f* d = (string_f*) b;
  return strcmp(c->value, d->value);
}

void get_freq(char** strings, int n, string_f** strings_freq) {
  *strings_freq = malloc(n * sizeof(string_f));
  strcpy((*strings_freq)->value, strings[0]);
  (*strings_freq)->frequency = 1;
  int current = 0;
  for(int i = 1; i < n; i++) {
    if(!strcmp(strings[i - 1], strings[i])) {
      (*strings_freq + current)->frequency++;
    } else {
      current++;
      strcpy((*strings_freq + current)->value, strings[i]);
      (*strings_freq + current)->frequency++;
    }
  }
}

void printArr_string_f(string_f* arr, int dim) {
  for(int i = 0; i < dim; i++) {
    printf("%s\n", arr[i].value);
  }
}

int main(int argc, char const *argv[]) {
  char** strings;
  int n;
  int k;
  readArr(&strings, &n, &k);

  string_f* strings_freq;
  qsort(strings, n, sizeof(char*), qsort_asc_string);
  get_freq(strings, n, &strings_freq);
  qsort(strings_freq, n, sizeof(string_f), qsort_dis_string_f_freq);
  qsort(strings_freq, k, sizeof(string_f), qsort_des_string_f_val);
  printArr_string_f(strings_freq, k);
  return 0;
}
