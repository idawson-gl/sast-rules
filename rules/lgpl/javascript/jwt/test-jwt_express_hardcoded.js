// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/jwt/jwt_express_hardcoded.js
// hash: e7a0a61
var jwt = require('express-jwt');

// ruleid:jwt_express_hardcoded
app.get('/protected', jwt({ secret: 'shhhhhhared-secret' }), function (req, res) {
    if (!req.user.admin) return res.sendStatus(401);
    res.sendStatus(200);
});

// ruleid:jwt_express_hardcoded
let hardcodedSecret = 'shhhhhhared-secret'

// ruleid:jwt_express_hardcoded
app.get('/protected2', jwt({ secret: hardcodedSecret }), function (req, res) {
    if (!req.user.admin) return res.sendStatus(401);
    res.sendStatus(200);
});

// ruleid:jwt_express_hardcoded
let secret = "hardcode"
// ruleid:jwt_express_hardcoded
const opts = Object.assign({ issuer: 'http://issuer' }, { secret })

app.get('/protected3', jwt(opts), function (req, res) {
    if (!req.user.admin) return res.sendStatus(401);
    res.sendStatus(200);
});
