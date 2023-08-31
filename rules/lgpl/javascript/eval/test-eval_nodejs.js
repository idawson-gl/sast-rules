// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/eval/eval_node.js
// hash: e7a0a61
var express = require('express');
var app = express();
app.get('/', function (req, res) {
    // ruleid:eval_nodejs
    var resp = eval("(" + req.query.name + ")");
    // ruleid:eval_nodejs
    var z = new Function('arg1', 'arg2', req.query.name)
    z(1, 2);
    // ruleid:eval_nodejs
    setTimeout('alert(' + req.body.name, 0);
    // ruleid:eval_nodejs
    setInterval(req.body.name, 0);
    res.send('Response</br>');
});
app.listen(8000);
eval("outside_express" + req.foo)
setTimeout('alert(' + req.body.name, 0);
setInterval(req.body.name, 0);
new Function('arg1', 'arg2', req.query.name)
