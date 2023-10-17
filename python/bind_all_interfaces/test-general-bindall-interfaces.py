# License: Apache 2.0 (c) PyCQA
# source: https://github.com/PyCQA/bandit/blob/master/examples/binding.py
# hash:  8eee173
import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('0.0.0.0', 31137))
s.bind(('192.168.0.1', 8080))
s.bind(('::', 8080))
s.bind(('127.0.0.1', 8080), ('', 8080))

unsafe = '0.0.0.0'
s.bind((unsafe, 8080))

s.bind(('127.0.0.1', 8080)) # safe

safe = '192.22.23.1'
s.bind((safe, 8080)) # safe
