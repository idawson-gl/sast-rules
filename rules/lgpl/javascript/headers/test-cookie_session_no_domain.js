// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/headers/header_cookie.js
// hash: e7a0a61
var session = require('express-session')
var express = require('express')
var app = express()

function test1() {
    var expiryDate = new Date(Date.now() + 60 * 60 * 1000) // 1 hour
    var opts = {
        keys: ['key1', 'key2'],
        cookie: {
            secure: true,
            sameSite: 'strict',
            httpOnly: true,
            domain: 'example.com',
            path: 'foo/bar',
            maxAge: 2000
        }
    }
    // ruleid:cookie_session_default
    app.use(session(opts))
}

function test2() {
    // ruleid:cookie_session_no_secure
    app.use(session(Object.assign({
        keys: ['key1', 'key2'],
        name: 'foo'
    }, {
        cookie: {
            httpOnly: true,
            sameSite: true,
            domain: 'example.com',
            path: 'foo/bar',
            maxAge: 2000
        }
    })))
}

function test3() {
    // ruleid:cookie_session_no_httponly
    app.use(session({
        keys: ['key1', 'key2'],
        name: 'foo',
        cookie: {
            httpOnly: false,
            secure: true,
            sameSite: 'lax',
            domain: 'example.com',
            path: 'foo/bar',
            maxAge: 2000
        }
    }))
}

function test4() {
    var opts = {
        keys: ['key1', 'key2'],
        name: 'foo',
    }

    if (app.get('env') === 'production') {
        app.set('trust proxy', 1) // trust first proxy
        opts.cookie = {
            secure: true,
            sameSite: 'strict',
            httpOnly: true,
            path: 'foo/bar',
            maxAge: 2000
        }
    }
    // ruleid:cookie_session_no_domain
    app.use(session(opts))
}

function test5() {
    var expiryDate = new Date(Date.now() + 60 * 60 * 1000) // 1 hour
    var opts = {
        keys: ['key1', 'key2'],
        name: 'foo',
        cookie: {
            secure: true,
            sameSite: 'strict',
            httpOnly: true
        }
    }

    if (app.get('env') === 'production') {
        app.set('trust proxy', 1) // trust first proxy
        opts.cookie.domain = 'example.com'
        opts.cookie.maxAge = 20000
    }

    // ruleid:cookie_session_no_path
    app.use(session(opts))
}

function test6() {
    var opts = {
        keys: ['key1', 'key2'],
        name: 'foo',
        cookie: {
            secure: true,
            sameSite: 'strict',
            httpOnly: true,
            domain: 'example.com',
            path: 'foo/bar'
        }
    }

    // ruleid:cookie_session_no_maxage
    app.use(session(opts))
}

function samestite() {
    var expiryDate = new Date(Date.now() + 60 * 60 * 1000) // 1 hour
    var opts = {
        keys: ['key1', 'key2'],
        name: 'foo',
        cookie: {
            secure: true,
            httpOnly: true,
            sameSite: 'none',
            domain: 'example.com',
            path: 'foo/bar',
            maxAge: 2000
        }
    }

    // ruleid:cookie_session_no_samesite
    app.use(session(opts))
}

