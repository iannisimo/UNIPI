#include <stdio.h>

int powMod(int a, int b, int p) {
    int v = 1;
    for(int i = 0; i < b; i++) {
        v = (v * a) % p;
    }
    return v;
}

int main(int argc, char const *argv[])
{
    printf("Provide m/c, e/d, n\n");
    int m, e, n;
    scanf("%d %d %d", &m, &e, &n);
    printf("The result is: %d\n", powMod(m, e, n));
    return 0;
}
