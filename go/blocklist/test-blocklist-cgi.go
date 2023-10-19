// License: Apache 2.0 (c) gosec
// source: https://github.com/securego/gosec/blob/master/testutils/source.go
// hash: bfb0f42

package main

import "net/http/cgi"

func maincgi() {
	if err := cgi.Serve(nil); err != nil {
		return
	}
}
