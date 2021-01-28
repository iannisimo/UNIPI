#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char const *argv[])
{
    int p;
    scanf("%d", &p);
    int rq[p];
    // rq = malloc(sizeof(int) * p);
    for(int i = 0; i < p; i++) {
        rq[i] = (i*i)%p;
        printf("%d\t->\t%d\n", i, rq[i]);
    }
    return 0;
}
