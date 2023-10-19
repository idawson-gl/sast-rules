// License: MIT (c) GitLab Inc.
#include<stdio.h>

int main() {
  printf("Hello HUMANS!");
}
void demo2() {
  char d[20];
  char s[20];
  int n;

  /* This is wrong, and should be flagged as risky: */
  MultiByteToWideChar(CP_ACP, 0, szName, -1, wszUserName, sizeof(wszUserName));
  /* This is also wrong, and should be flagged as risky: */
  MultiByteToWideChar(CP_ACP, 0, szName, -1, wszUserName, sizeof wszUserName);
  /* This is much better: */
  MultiByteToWideChar(CP_ACP, 0, szName, -1, wszUserName, sizeof(wszUserName) / sizeof(wszUserName[0]));
  /* This is much better: */
  MultiByteToWideChar(CP_ACP, 0, szName, -1, wszUserName, sizeof wszUserName / sizeof(wszUserName[0]));
}