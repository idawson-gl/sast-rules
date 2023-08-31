// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/generic/logic_bypass.js
// hash: e7a0a61
var express = require('express');
var app = express();
app.get('/view/:id', function (req, res) {

    // ruleid:node_logic_bypass
    if (req.cookies["user"] === req.params["id"]) {
        showProfile();
    }

});
