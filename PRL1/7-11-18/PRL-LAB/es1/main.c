#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define N_CATS 4

enum t_food {crocchette, scatolette, tonno};

typedef struct cat {
  int id;
  int age;
  float weight;
  int food;
} t_cat;

char* getFood(int type) {
  char* out;
  out = (char*) malloc(sizeof(char) * 11);
  if(type == crocchette) {
    strcpy(out, "crocchette");
  } else if(type == scatolette) {
    strcpy(out, "scatolette");
  } else if(type == tonno) {
    strcpy(out, "tonno");
  }
  return out;
}

int main(int argc, char const *argv[]) {
  t_cat cats[N_CATS];
  int i;
  for(i = 0; i < N_CATS; i++) {
    scanf("%d %d %f %d", &cats[i].id, &cats[i].age, &cats[i].weight, &cats[i].food);
  }

  float avgWeight = 0;
  for(i = 0; i < N_CATS; i++) {
    avgWeight += cats[i].weight;
  }
  avgWeight /= N_CATS;

  for(i = 0; i < N_CATS; i++) {
    if(cats[i].weight > avgWeight && cats[i].age < 4) {
      printf("%d %s\n", cats[i].id, getFood(cats[i].food));
    }
  }
  return 0;
}
