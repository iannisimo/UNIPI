#include <stdio.h>

#define COUNTER_L 256

void reset(int array[], int len);
int allZero(int array[], int len);
int anagramma(unsigned char *s1, unsigned char *s2);

int main(int argc, char const *argv[]) {
  char str1[1000], str2[1000];

  scanf("%s %s", str1, str2);

  printf("%d\n", anagramma(str1, str2));
  return 0;
}

void reset(int array[], int len) {
  for(int i = 0; i < len; i++) {
    array[i] = 0;
  }
}

int anagramma(unsigned char *s1, unsigned char *s2) {
  int counter[COUNTER_L];
  reset(counter, COUNTER_L);
  int i = 0;
  while(s1[i] != '\0') {
    counter[s1[i]]++;
    i++;
  }
  i = 0;
  while(s2[i] != '\0') {
    counter[s2[i]]--;
    i++;
  }
  return allZero(counter, COUNTER_L);
}

int allZero(int array[], int len) {
  int found = 0;
  int i = 0;
  while(!found && i < len) {
    if(array[i] != 0) found = 1;
    else              i++;
  }
  return !found;
}
