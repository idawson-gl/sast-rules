#!/usr/bin/env python3
# License: MIT (c) GitLab Inc.

import logging

def good():
    while True:
        try:
            v = 1
        except Exception as k:
            logging.info("Info level logging")
        except Exception:
            logging.info("Info level logging")
    
def bad():
    condition = 10 > 0
    while condition:
        try:
            v = 1
        except Exception:
            logging.info("Info level logging")
        except Exception: pass
        except Exception as e:
            pass
        except Exception as k:
            logging.info("Info level logging") 
        finally:
            logging.info("blah")
        
def bad2():
    keep_going = 1 > 0
    while keep_going:
        try:
            v = 0
            try:
                res = 1/v
            except Exception:
                pass
        except Exception as e:
            logging.error("blah", e)

