// License: MIT (c) GitLab Inc.
#include <stdio.h>

#include <stdarg.h>

void sfind(char * string, char * format, ...) {
  va_list arglist;

  va_start(arglist, format);
  vsscanf(string, format, arglist);
  va_end(arglist);
}

void ffind(FILE * fp, char * format, ...) {
  va_list arglist;

  va_start(arglist, format);
  vfscanf(fp, format, arglist);
  va_end(arglist);
}

int main() {
  FILE * fp;
  int i;
  char buff[255];
  char foo[100];
  char week[10];
  int day, year;
  char weekday[10], month[12];
  fp = fopen("file.txt", "r");
  sscanf(foo, "%s", week);
  while (fscanf(fp, "%s", buff) != EOF) {
    printf("%s ", buff);
  }
  fclose(fp);

  sfind("Saturday April 18 1987", "%s %s %d %d", weekday, month, & day, & year);
  printf("\n%s, %s %d, %d\n", weekday, month, day, year);
  ffind(stdin, "%s %s %d %d", weekday, month, & day, & year);
  printf("\n%s, %s %d, %d\n", weekday, month, day, year);
  return 0;
}