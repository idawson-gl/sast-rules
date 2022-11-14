#!/usr/bin/env python
# License: MIT (c) GitLab Inc.

try:
    v = 1
except Exception:
    logging.info("Info level logging")
except Exception: pass
except Exception as e:
    pass

