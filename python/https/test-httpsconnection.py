#!/usr/bin/env python
# License: Apache 2.0 (c) PyCQA
# source: https://github.com/PyCQA/bandit/blob/master/examples/httplib_https.py
# hash:  8eee173

import httplib
c = httplib.HTTPSConnection("example.com")

import http.client
c = http.client.HTTPSConnection("example.com")

import six
six.moves.http_client.HTTPSConnection("example.com")
