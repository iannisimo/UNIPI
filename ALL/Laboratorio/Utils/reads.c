// Strings
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

// Strange structs
typedef struct {
  int x;
  int y;
  int c;
} point;

typedef struct {
  int x1;
  int x2;
  int y1;
  int y2;
} rect;

point* readPoints(int n) {
  point* out;
  out = malloc(n * sizeof(point));
  for(int i = 0; i < n; i++) {
    scanf("%d %d %d", &(out[i].x), &(out[i].y), &(out[i].c));
  }
  return out;
}

rect readRect() {
  rect out;
  scanf("%d %d %d %d", &(out.x1), &(out.y1), &(out.x2), &(out.y2));
  return out;
}

// Vect2D
int readArr_vect2d(vect2d** arr) {
  int out;
  scanf("%d", &out);
  *arr = malloc(sizeof(vect2d) * out);
  for(int i = 0; i < out; i++) {
    scanf("%d %d", &(*arr+i)->x, &(*arr+i)->y);
  }
  return out;
}
