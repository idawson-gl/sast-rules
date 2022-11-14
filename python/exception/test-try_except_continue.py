#!/usr/bin/env python3
# License: MIT (c) GitLab Inc.

x = 0

while x < 1:
    try:
        x = 1 
    except Exception as e:
        continue
    except Exception: continue
    except Exception:
        x = 1
