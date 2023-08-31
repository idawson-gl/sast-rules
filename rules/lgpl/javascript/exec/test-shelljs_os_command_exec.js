// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/exec/exec_shelljs.js
// hash: e7a0a61
const shell = require('shelljs');
const express = require('express')
const router = express.Router()

router.get('/greeting', (req, res) => {
    // ruleid:shelljs_os_command_exec
    return shell.exec(req.query, { silent: true })
})

router.get('/foo', (req, res) => {
    // ruleid:shelljs_os_command_exec
    const input = `ls ${req.query}`
    return shell.exec(input, { silent: true })
})
