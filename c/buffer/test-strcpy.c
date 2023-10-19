// License: MIT (c) GitLab Inc.

#include <stdio.h>

#include <string.h>

int main() {
  char string1[20] = "Finding flaw";
  char string2[20];

  // should report this
  strcpy(string2, string1);

  puts(string2);

  return 0;
}