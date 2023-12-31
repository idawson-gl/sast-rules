#!/usr/bin/env python
# License: Apache 2.0 (c) PyCQA
# source: https://github.com/PyCQA/bandit/blob/master/examples/hashlib_new_insecure_functions.py
# hash:  8eee173

import hashlib

hashlib.new('md5')
hashlib.new('md4', 'test')
hashlib.new(name='md5', string='test')
hashlib.new('MD4', string='test')
hashlib.new(string='test', name='MD5')
hashlib.new('sha1')
hashlib.new(string='test', name='SHA1')
hashlib.new('sha', string='test')
hashlib.new(name='SHA', string='test')

# Test that plugin does not flag valid hash functions.
hashlib.new('sha256')
hashlib.new('SHA512')

