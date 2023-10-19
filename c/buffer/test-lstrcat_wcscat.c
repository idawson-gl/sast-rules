// License: MIT (c) GitLab Inc.
#include <stdio.h>


void main() {
  char * ptr = NULL;
  wchar_t display[10];
  unsigned char another[10];
  char fstr[80] = "This is the first part. ";
  string str2 = "This is the second part.";
  ptr = lstrcat(fstr, str2);
  wcscat(display, L " ");
  _mbscat(another, T " ");

}