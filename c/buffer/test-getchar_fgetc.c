// License: MIT (c) GitLab Inc.

#include <stdio.h>

#include <sys/types.h>

#include <unistd.h>

int main() {
  char c, t, p;
  char buf[20];
  size_t nbytes;
  ssize_t bytes_read;
  int fd;
  //getchar
  FILE * fp = fopen("test.txt", "r");
  printf("Try getchar: ");
  c = getchar();
  //fgetc
  char t = fgetc(fp);
  printf("You entered");
  putchar(c);
  //getc
  printf("Try getc: ");
  p = getc(stdin);
  printf("You entered");
  putc(p, stdout);
  //read
  nbytes = sizeof(buf);
  bytes_read = read(fd, buf, nbytes);

  return (0);
}