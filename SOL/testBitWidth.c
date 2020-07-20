#include <stdio.h>

struct fields {
	unsigned int color :3;
	unsigned int style :2;
	int								 :3;
	char cell;
};

int main() {
	struct fields F;
	F.color = 3;
	F.style = 2;
	F.cell = 0;
	printf("%d\n", *(int*)&F);
	return 0;
}
