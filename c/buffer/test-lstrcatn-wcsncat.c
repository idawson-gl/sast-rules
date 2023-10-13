// License: MIT (c) GitLab Inc.

#include<stdio.h>

void main() {
  wchar_t src[] = L "\u0410\u0411\u0412\u0413\u0415\u0416\u0417";
  wchar_t dest[] = L "\u0424\u0425\u0426\u0427\u0428\u0429";

  wcsncat(dest, src, 4);
  _tcsncat(dest, src, sizeof(dest));
  return 0;
}