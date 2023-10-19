# License: Apache 2.0 (c) PyCQA
# source: https://github.com/PyCQA/bandit/blob/master/examples/hardcoded-tmp.py
# hash:  8eee173


# ok
with open('/tmp', 'w') as f:
    f.write('def')

# ok
with open('/abc/tmp', 'w') as f:
    f.write('def')

# ruleid:bandit.B108-2
with open('/var/tmp/123', 'w') as f:
    f.write('def')

# ruleid:bandit.B108-2
with open('/dev/shm/unit/test', 'w') as f:
    f.write('def')

# Negative test
with open('/foo/bar', 'w') as f:
    f.write('def')

with open("/dev/shm/sample", "w", encoding="utf8") as fp:
    fp.write("Test!")

with open("/dev/null", "w", encoding="utf8") as fp:
    fp.write("Test!")

with open("/var/folders/tb/m98lx67j5v/T/tmpfqlj3_eb", "w", encoding="utf8") as fp:
    fp.write("Test!")

with open("/var/run/secrets/kubernetes.io/serviceaccount/namespace", "r", encoding="utf8") as fp:
    fp.read()
