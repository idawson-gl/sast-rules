// License: MIT (c) GitLab Inc.

#include <stdlib.h>

#include <stdio.h>

#include <string.h>

int main() {
  printf("Hello HUMANS!");
}
void demo2() {
  char d[20];
  char s[20];
  char buffer[80];
  int n;

  memcpy(d, s);
  CopyMemory(d, s);
  bcopy("Hello ", buffer, 6);
  bcopy("world", & buffer[6], 6);
  printf("%s\n", buffer);
}