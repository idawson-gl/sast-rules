// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/generic/error_disclosure.js
// hash: e7a0a61
app.get('/', function (req, res) {
    try {
        foo;
    }
    catch (err) {
        res.statusCode = 500;
        res.setHeader("Content-Type", "text/plain");
        // ruleid:node_error_disclosure
        res.end(err.stack);
        return;
    }
});


// ruleid:generic_error_disclosure
try {
    throw new Error("Something unexpected has occurred.");
} catch (e) {
    console.error(e);
}

// ruleid:generic_error_disclosure
console.trace("baad")
