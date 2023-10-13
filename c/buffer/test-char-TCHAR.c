// License: MIT (c) GitLab Inc.
#include <locale.h>

#include <wchar.h>

#include <stdio.h>

int main() {
  static wchar_t s[100];
  static wchar_t t[100] = {};
  static char a[50];
  static char b[50] = {};
  static TCHAR c[20];
  static TCHAR d[20] = {};
  return 0;
}