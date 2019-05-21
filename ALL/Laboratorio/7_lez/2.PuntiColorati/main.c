  #include <stdio.h>
#include <stdlib.h>

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


point* readPoints(int);
rect readRect();

int countColors(point*, int, rect);

int ascendingC(const void* a, const void* b);


int main(int argc, char const *argv[]) {
  int n;
  int m;
  scanf("%d %d", &n, &m);
  point *points = readPoints(n);

  qsort(points, n, sizeof(point), ascendingC);

  for(int i = 0; i < m; i++) {
    rect rct = readRect();
    printf("%d\n", countColors(points, n, rct));
  }

  return 0;
}

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


int countColors(point* pts, int n, rect rct) {
  int out = 0;
  int lastC = -1;
  for(int i = 0; i < n; i++) {
    if(pts[i].x >= rct.x1 && pts[i].x <= rct.x2 && pts[i].y >= rct.y1 && pts[i].y <= rct.y2 && pts[i].c != lastC) {
      lastC = pts[i].c;
      out++;
    }
  }
  return out;
}

int ascendingC(const void* a, const void* b) {
  return ((point*)a)->c - ((point*)b)->c;
}
