#include <stdio.h>
#include <math.h>

typedef struct _t {
    int d;
    int x;
    int y;
} triple;

void printAll (int a, int b, triple t) {
    printf("EE(%d, %d)\t->\t<%d, %d, %d>\n", a, b, t.d, t.x, t.y);
}

triple ee(int a, int b, int i) {
    if (b == 0) {
        triple r = {a, 1, 0};
        printAll(a, b, r);
        return r;
    }
    triple t = ee(b, a%b, i+1);
    triple r;
    r.d = t.d;
    r.x = t.y;
    r.y = t.x - round(a/b)*t.y;
    printAll(a, b, r);
    return r;
}

int main(int argc, char const *argv[]) {
    int s;
    int n;
    scanf("%d", &s);
    scanf("%d", &n);
    triple a = ee(s, n, 0);
    int inv = a.x > 0 ? a.x : n + a.x;
    printf("\n\nThe inverse of %d (mod%d) is: %d\n", s, n, inv);
    return 0;
}
