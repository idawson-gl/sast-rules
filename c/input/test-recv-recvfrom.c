// License: MIT (c) GitLab Inc.
#include<stdio.h>

#include<stdlib.h>

#include <sys/types.h>

#include <sys/uio.h>

#include <unistd.h>


struct student {
  char name[50];
  char stream[50];
  int rollno;
}
std;

int main() {
  //readv 
  ssize_t bytes_read;
  int fd;
  char buf0[20];
  int iovcnt;
  struct iovec iov[3];
  iov[0].iov_base = buf0;
  iov[0].iov_len = sizeof(buf0);
  iovcnt = sizeof(iov) / sizeof(struct iovec);

  bytes_read = readv(fd, iov, iovcnt);
  //fread
  FILE * fp;
  fp = fopen("students.txt", "rb");

  printf("Testing fread() function: \n\n");

  while (fread( & std, sizeof(std), 1, fp) == 1) {
    printf("Name: %s \n", std.name);
    printf("stream: %s \n", std.stream);
    printf("rollno: %d \n", std.rollno);
  }

  fclose(fp);
  return 0;
}