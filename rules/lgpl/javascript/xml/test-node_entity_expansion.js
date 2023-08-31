// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/xml/xml_entity_expansion_dos.js
// hash: e7a0a61
app.get('/expat', function (req, res) {
    // ruleid:node_entity_expansion
    var parser = new expat.Parser();
    parser.write(req.param("xml"));
})
