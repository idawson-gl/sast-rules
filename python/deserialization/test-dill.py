#!/usr/bin/env python
# License: MIT (c) GitLab Inc.

import dill

cube = lambda x: x * x *x
pickle_ans = dill.dumps(cube)
print(pickle_ans)
