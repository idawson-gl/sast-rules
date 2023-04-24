// License: MIT (c) GitLab Inc.
#include <stdio.h>

#include <unistd.h>

#include <stdlib.h>

#include <getopt.h>

int main(int argc, char * argv[]) {
  int option;
  int opt = 0;
  int area = -1, perimeter = -1, breadth = -1, length = -1;
  int long_index = 0;

  while ((option = getopt(argc, argv, ":if:lrx")) != -1) {
    switch (option) {

    case 'i':
    case 'l':
    case 'r':
      printf("Given Option: %c\n", option);
      break;
    case 'f':
      printf("Given File: %s\n", optarg);
      break;
    case ':':
      printf("option needs a value\n");
      break;
    case '?':
      printf("unknown option: %c\n", optopt);
      break;
    }
  }
  static struct option long_options[] = {
    {
      "area",
      no_argument,
      0,
      'a'
    },
    {
      "perimeter",
      no_argument,
      0,
      'p'
    },
    {
      "length",
      required_argument,
      0,
      'l'
    },
    {
      "breadth",
      required_argument,
      0,
      'b'
    },
    {
      0,
      0,
      0,
      0
    }
  };

  while ((opt = getopt_long(argc, argv, "apl:b:",
      long_options, & long_index)) != -1) {
    switch (opt) {
    case 'a':
      area = 0;
      break;
    case 'p':
      perimeter = 0;
      break;
    case 'l':
      length = atoi(optarg);
      break;
    case 'b':
      breadth = atoi(optarg);
      break;
    default:
      exit(EXIT_FAILURE);
    }
  }
  return 0;
}