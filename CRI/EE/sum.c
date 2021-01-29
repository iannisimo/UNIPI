#include <stdio.h>
#include <math.h>

typedef struct _t {
    int d;
    int x;
    int y;
} triple;

triple ee(int a, int b) {
    if (b == 0) {
        triple r = {a, 1, 0};
        return r;
    }
    triple t = ee(b, a%b);
    triple r;
    r.d = t.d;
    r.x = t.y;
    r.y = t.x - round(a/b)*t.y;
    return r;
}

int inverse(int a, int m) {
    triple eeVal = ee(a, m);
    return eeVal.x;
}

int getPositive(int v, int m) {
    return v > 0 ? v : m + v;
}

int main(int argc, char const *argv[])
{
    int p, a, b, x1, y1, x2, y2;
    printf("Digitare p, a, b, valori della curva, x1, y1, x2, y2, punti da sommare\n");
    scanf("%d %d %d %d %d %d %d", &p, &a, &b, &x1, &y1, &x2, &y2);
    int l;
    if(x1 != x2 || y1 != y2) {
        l = ((y2-y1) * inverse(x2 - x1, p)) % p;
    } else {
        l = ((3 * (x1 * x1) + a) * inverse(2*y1, p)) % p;
    }
    int xs = ((l*l) - x1 - x2) % p;
    int ys = (l * (x1 - xs) - y1) % p;
    printf("\nLambda: %d\tS: (%d, %d)\n", getPositive(l, p), getPositive(xs, p), getPositive(ys, p));

    return 0;
}
