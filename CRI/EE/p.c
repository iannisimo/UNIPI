#include <stdio.h>

int main(int argc, char const *argv[])
{
    int p[] = {2,3,5,7,11,13,17,19,23,29,31,37};
    int n;
    scanf("%d", &n);
    for(int i = 0; i < (sizeof(p)/sizeof(int)); i++) {
        for(int j = 0; j < (sizeof(p)/sizeof(int)); j++) {
            if(p[i]*p[j] == n) {
                printf("%d, %d\n", p[i], p[j]);
                return 0;
            }
        }
    }
    printf("NaN\n");
    return 1;
}
