// QSorts
// Strings and String_f struct
int qsort_asc_string(const void* a, const void* b) {
  return -1 * strcmp(*(char**) a, *(char**)b);
}
int qsort_des_string_f_freq(const void* a, const void* b) {
  string_f* c = (string_f*) a;
  string_f* d = (string_f*) b;
  return d->frequency - c->frequency;
}
int qsort_des_string_f_val(const void* a, const void* b) {
  string_f* c = (string_f*) a;
  string_f* d = (string_f*) b;
  return strcmp(c->value, d->value);
}

// Vect2D
int qsort_vect2d(const void* a, const void* b) {
  vect2d* c = (vect2d*) a;
  vect2d* d = (vect2d*) b;
  if(c->x == d->x) {
    return d->y - c->y;
  }
  return c->x - d->x;
}

// Strange structs
typedef struct {
  int x;
  int y;
  int c;
} point;

int ascendingC(const void* a, const void* b) {
  return ((point*)a)->c - ((point*)b)->c;
}

typedef struct {
  char name[101];
  int credits;
  int difficulty;
} exam;

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
