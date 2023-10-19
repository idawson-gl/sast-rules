// License: MIT (c) GitLab Inc.
#include <stdio.h>

#include <unistd.h>

#include <stdlib.h>

int main(int argc, char * argv[]) {
  int output = chroot(".");

  if (output == 0)
    printf("Chroot Succese.\n");
  return 0;
}