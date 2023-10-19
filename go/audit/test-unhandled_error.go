// License: Apache 2.0 (c) gosec
// source: https://github.com/securego/gosec/blob/master/testutils/source.go
// hash: bfb0f42

package main

import (
	"fmt"
	"io/ioutil"
	"os"
)

func test() (int, error) {
	return 0, nil
}

func main() {
	v, _ := test()
	fmt.Println(v)
	_ = a()
	a()
	b()
	c()
}

func a() error {
	return fmt.Errorf("This is an error")
}

func b() {
	fmt.Println("b")
	ioutil.WriteFile("foo.txt", []byte("bar"), os.ModeExclusive)
}

func c() string {
	return fmt.Sprintf("This isn't anything")
}
