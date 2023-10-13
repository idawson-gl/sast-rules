// License: MIT (c) GitLab Inc.
#include <stdio.h>

int main() {
  char string[50];
  printf("Please enter your str input");
  gets(string);
  printf("Your input: %s", string);

  return 0;
}