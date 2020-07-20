#include <stdio.h>

int staticVar = 0; // a static variable
int main() {
	int localVar = 0; // a procedure local variable
	staticVar += 1; localVar += 1;
	sleep(1); // sleep causes the program to wait for x seconds
	printf ("static address: %x, value: %d\n", &staticVar, staticVar);
	printf ("procedure local address: %x, value: %d\n", &localVar, localVar);
}
