#include <stdio.h>

#define N_EMPLOYEES 2

#define MIN_MONTH 5
#define MIN_YEAR 2000

typedef struct employee {
  int id;
  int recMonth;
  int recYear;
  int salary;
} t_employee;

int beforeDate(int monthA, int yearA, int monthB, int yearB) {
  int out = 0;
  if(yearA < yearB || (yearA == yearB && monthA < monthB)) {
    out = 1;
  }
  return out;
}

int main(int argc, char const *argv[]) {
  t_employee employees[N_EMPLOYEES];
  int salaryInc;
  int i;
  for(i = 0; i < N_EMPLOYEES; i++) {
    scanf("%d %d %d %d", &employees[i].id, &employees[i].recMonth, &employees[i].recYear, &employees[i].salary);
  }
  scanf("%d", &salaryInc);
  for(i = 0; i < N_EMPLOYEES; i++) {
    if(beforeDate(employees[i].recMonth, employees[i].recYear, MIN_MONTH, MIN_YEAR)) {
      employees[i].salary += (employees[i].salary * salaryInc) / 100;
    }
    if(employees[i].salary > 1200) {
      printf("%d %d\n", employees[i].id, employees[i].salary);
    }
  }
  return 0;
}
