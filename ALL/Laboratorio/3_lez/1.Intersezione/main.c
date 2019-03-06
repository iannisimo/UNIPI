#include <stdio.h>
#include <stdlib.h>

void readArray(int** arr, int* dim);
int  arrayIntersections(int* arr_1, int dim_1, int* arr_2, int dim_2);
int  binaryMember(int *arr, int sx, int dx, int val);
void mergeSort(int* arr, int dim);
void iterativemergesort(int a[],int size);
void merge(int a[], int start, int center, int end, int size);

int main(int argc, char const *argv[]) {
  int* arr_a; int dim_a;
  int* arr_b; int dim_b;
  readArray(&arr_a, &dim_a);
  readArray(&arr_b, &dim_b);
  int out = arrayIntersections(arr_a, dim_a, arr_b, dim_b);
  printf("%d\n", out);
  return 0;
}

void readArray(int** arr, int* dim) {
  scanf("%d\n", dim);
  *arr = malloc(sizeof(int) * (*dim));
  for(int i = 0; i < (*dim); i++) {
    scanf("%d", *arr + i);
  }
}

int  arrayIntersections(int* arr_i, int dim_i, int* arr_j, int dim_j) {
  int out = 0;
  iterativemergesort(arr_j, dim_j);
  for(int i = 0; i < dim_i; i++) {
    out += binaryMember(arr_j, 0, dim_j, arr_i[i]);
  }
  return out;
}

int  binaryMember(int *arr, int sx, int dx, int val) {
  while(sx <= dx) {
    int cx = (sx + dx) / 2;
    if (val == arr[cx]) {
      return 1;
    }
    if(val < arr[cx]) {
      dx = cx - 1;
    } else {
      sx = cx + 1;
    }
  }
  return 0;
}

void merge(int a[], int start, int center, int end, int size) {
	int i, j, k;
	int app[size];

	i = start;
	j = center+1;
	k = 0;

	while ((i<=center) && (j<=end)) {
		if (a[i] <= a[j]) {
			app[k++] = a[i++];
		} else {
			app[k++] = a[j++];
		}
	}

	while (i<=center)
		app[k++] = a[i++];

	while (j<=end)
		app[k++] = a[j++];

	for (k=start; k<=end; k++)
		a[k] = app[k-start];

	// printf("merging.. {v[%d] - v[%d]} whit {v[%d] - v[%d]} \n",start,center,center+1,end);
}

void iterativemergesort(int a[],int size) {
	int sizetomerge=size-1;
	size--;
	int i;
	int n=2;

	while (n<sizetomerge*2) {
		for (i=0; (i+n-1)<=sizetomerge; i+=n) {
			merge(a,i,(i+i+n-1)/2,i+(n-1),sizetomerge);
		}

		i--;
		if ((sizetomerge+1)%n!=0) {
			if (size>sizetomerge)
				merge (a,sizetomerge -((sizetomerge)%n),sizetomerge,size,size);
			sizetomerge=sizetomerge-((sizetomerge+1)%n);}
		n=n*2;
	}

	if (size>sizetomerge)
		merge (a,0,size-(size-sizetomerge),size,size);
}
