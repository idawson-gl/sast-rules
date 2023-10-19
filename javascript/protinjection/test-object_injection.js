// License: MIT (c) GitLab Inc.

function negative(someVar, obj) {
  two = 2
  string = "key"

  value = obj.whyNot
  value = obj['1']
  value = obj[two]
  value = obj[string]
  value = obj[7]
}

function foo(someVar, obj) {
  // False Positives
  three = [3]
  six = {s: 6}

  value = obj[three[0]]
  value = obj[six.s]
  value = obj[six['s']]

  // True Positives
  someObjVar = {s: someVar}
  someArrVar = [someVar]

  value = obj[someVar]
  value = obj[someObjVar.s]
  value = obj[someObjVar['s']]
  value = obj[someArrVar[0]]
}
