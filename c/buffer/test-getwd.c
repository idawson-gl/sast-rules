// License: MIT (c) GitLab Inc.

#include <stdlib.h>

#include <unistd.h>

int main() {
  char * buf;
  char * ptr;

  ptr = getwd(buf);
  return 0;
}