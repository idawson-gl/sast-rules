// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/jwt/jwt_none_algorithm.js
// hash: e7a0a61
// ruleid:node_jwt_none_algorithm
const jose = require("jose");
const { JWK, JWT } = jose;
const token = JWT.verify('token-here', JWK.None);

function verifyJwt() {
    // ruleid:node_jwt_none_algorithm
    let jwt = require("jsonwebtoken");
    let secret = 'some-secret';
    jwt.verify('token-here', secret, { algorithms: ['RS256', 'none'] }, function (err, payload) {
        console.log(payload);
    });
}

