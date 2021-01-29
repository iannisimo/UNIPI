#include <stdio.h>

// Merges two subarrays of arr[].
// First subarray is arr[l..m]
// Second subarray is arr[m+1..r]
void merge(int arr[], int l, int m, int r)
{
    int i, j, k;
    int n1 = m - l + 1;
    int n2 = r - m;
 
    /* create temp arrays */
    int L[n1], R[n2];
 
    /* Copy data to temp arrays L[] and R[] */
    for (i = 0; i < n1; i++)
        L[i] = arr[l + i];
    for (j = 0; j < n2; j++)
        R[j] = arr[m + 1 + j];
 
    /* Merge the temp arrays back into arr[l..r]*/
    i = 0; // Initial index of first subarray
    j = 0; // Initial index of second subarray
    k = l; // Initial index of merged subarray
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        }
        else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }
 
    /* Copy the remaining elements of L[], if there
    are any */
    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }
 
    /* Copy the remaining elements of R[], if there
    are any */
    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }
}
 
/* l is for left index and r is right index of the
sub-array of arr to be sorted */
void mergeSort(int arr[], int l, int r)
{
    if (l < r) {
        // Same as (l+r)/2, but avoids overflow for
        // large l and h
        int m = l + (r - l) / 2;
 
        // Sort first and second halves
        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
 
        merge(arr, l, m, r);
    }
}



int pow_i(int v, int p) {
    int r = 1;
    for(int i = 0; i < p; i++) {
        r *= v;
    }
    return r;
}

int contains(int e, int arr[], int dim) {
    for(int i = 0; i < dim; i++) {
        if(arr[i] == e) return 1;
    }
    return 0;
}

int isCurve(int a, int b, int p) {
    if((4*pow_i(a, 3) + 27 * pow_i(b, 2)) % p != 0) return 1;
    return 0;
}

void rqCalc(int v, int arr[]) {
    for(int i = 0; i < v; i++) {
        arr[i] = (i*i) % v;
    }
}

void printAll(int arr[], int dim) {
    for(int i = 0; i < dim; i++) {
        printf("arr[%d]\t= %d\n", i, arr[i]);
    }
}

void printAllInline(int arr[], int dim) {
    printf("\n");
    for(int i = 0; i < dim; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int tellMeYSquared(int x, int a, int b, int p) {
    return (pow_i(x, 3) + a*x + b) % p;
}

int main(int argc, char const *argv[])
{
    int p, a, b;
    printf("Provide p, a, b\n");
    scanf("%d %d %d", &p, &a, &b);
    if(!isCurve(a, b, p)) {
        printf("That ain't a fucking curve\n");
        return 1;
    }
    printf("\nBingo man! That's what I call a curve!\n\n");
    int rq[p];
    rqCalc(p, rq);
    printf("Residui:\n\n");
    printAll(rq, p);
    printf("\n");
    int order = 1;
    printf("x\ty^2\ty\n\n");
    for(int x = 0; x < p; x++) {
        int ySq = tellMeYSquared(x, a, b, p);
        if(!contains(ySq, rq, p)) {
            printf("%d\t%d\t---\n", x, ySq);
            continue;
        }
        printf("%d\t%d\t", x, ySq);
        for(int i = 0; i < p; i++) {
            if(rq[i] == ySq) {
                printf("%d ", i);
                order++;
            }
        }
        printf("\n");
    }

    printf("\nL'ordine della curva e' %d\n", order);

    mergeSort(rq, 0, p-1);
    printAllInline(rq, p);

    return 0;
}
