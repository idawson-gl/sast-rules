#!/usr/bin/env python
# License: Apache 2.0 (c) PyCQA
# source: https://github.com/PyCQA/bandit/blob/master/examples/tempnam.py
# hash:  8eee173

from os import tempnam
from os import tmpnam
import os

os.tmpnam()

tmpnam()

os.tempnam('dir1')
os.tempnam('dir1', 'prefix1')

tempnam('dir1')
tempnam('dir1', 'prefix1')
