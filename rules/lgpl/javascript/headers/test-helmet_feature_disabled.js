// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/headers/header_helmet_disabled.js
// hash: e7a0a61
// ruleid:helmet_feature_disabled
app.use(helmet({
    frameguard: false,
}))


// ruleid:helmet_feature_disabled
app.use(helmet({
    "xssFilter": false
}))
