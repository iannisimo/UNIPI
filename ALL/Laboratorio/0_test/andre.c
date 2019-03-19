#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int binarySearch(char *el, char **arr, int first, int last);

int main(){
    char **arr;
    char *findit;
    int dim, command;
    int i, j;


    scanf("%d", &dim);

    arr = malloc(dim * sizeof(char*));
    findit = malloc(100 * sizeof(char));

    for(i = 0; i < dim; i++){
        arr[i] = malloc(100 * sizeof(char));
        scanf("%s", arr[i]);
    }

    scanf("%d", &command);

    while(command != 0){
        scanf("%s", findit);
        printf("%d\n", binarySearch(findit, arr, 0, dim-1));
        scanf("%d", &command);
    }

    return 0;
}

int binarySearch(char *el, char **arr, int first, int last){
    if(first == last){
        if(strcmp(el, arr[first]) == 0){
            return first;
        }else{
            return -1;
        }
    }else{
        int center;
        int check;
        center = (first + last)/2;
        check = strcmp(el, arr[center]);

        if(check < 0){
            return binarySearch(el, arr, first, center - 1);
        }else if(check > 0){
            return binarySearch(el, arr, center + 1, last);
        }else{
            return center;
        }
    }
}
