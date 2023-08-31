// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/dos/regex_injection.js
// hash: e7a0a61
var express = require('express');
var app = express();

app.get('/search', function (req, res) {
    // ruleid:regex_injection_dos
    var key = req.param("key");
    // Regex created from user input
    var re = new RegExp("\\b" + key);
});
//do not detect this
var re = new RegExp("\\b" + key + "=(.*)\n");
