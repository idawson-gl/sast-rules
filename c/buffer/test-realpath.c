// License: MIT (c) GitLab Inc.
#include <stdlib.h>

#include <stdio.h>

void main() {

  char * symlinkpath = "/tmp/symlink/file";
  char * actualpath;

  actualpath = realpath(symlinkpath, NULL);

}