#include <stdio.h>

int main(int argc, char const *argv[])
{
    int n;
    int m;
    scanf("%d %d", &n, &m);
    printf("%d mod %d = %d\n", n, m, n%m);
    return 0;
}
