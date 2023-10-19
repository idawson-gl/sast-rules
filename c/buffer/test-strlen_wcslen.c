// License: MIT (c) GitLab Inc.
#include<stdio.h>

#include <string.h>

int main() {
  char ch[] = {
    'g',
    'i',
    't',
    '\0'
  };

  printf("Length of the string is: %d", strlen(ch));

  return 0;
}