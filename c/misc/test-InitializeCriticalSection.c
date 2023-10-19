// License: MIT (c) GitLab Inc.
#include <stdlib.h>

#include <stdio.h>

#include <windows.h>

CRITICAL_SECTION CriticalSection;

int main(int argc, char * argv[]) {
  InitializeCriticalSection( & CriticalSection);
  return 0;
}