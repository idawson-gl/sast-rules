// License: Apache 2.0 (c) gosec
// source: https://github.com/securego/gosec/blob/master/testutils/source.go
// hash: bfb0f42

package main

import "fmt"

var vector []*string

func appendVector(s *string) {
	vector = append(vector, s)
}

func printVector() {
	for _, item := range vector { fmt.Printf("%s", *item) }
	fmt.Println()
}

func foo() (int, **string, *string) {
	for _, item := range vector { return 0, &item, item }
	return 0, nil, nil
}

func appendrange() {
	for _, item := range []string{"A", "B", "C"} { appendVector(&item) }

	printVector()

	zero, c_star, c := foo()
	fmt.Printf("%d %v %s", zero, c_star, c)
}

func saferange() {
	sampleMap := map[string]string{}
	sampleString := "A string"
	for sampleString, _ = range sampleMap { fmt.Println(sampleString) }
}
