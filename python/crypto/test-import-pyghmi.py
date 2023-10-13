#!/usr/bin/env python
# License: Apache 2.0 (c) PyCQA
# source: https://github.com/PyCQA/bandit/blob/main/examples/pyghmi.py

from pyghmi.ipmi import command

cmd = command.Command(bmc="bmc",
                      userid="userid",
                      password="ZjE4ZjI0NTE4YmI2NGJjZDliOGY3ZmJiY2UyN2IzODQK")
