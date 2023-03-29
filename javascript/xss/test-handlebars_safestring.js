// License: MIT (c) GitLab Inc.
// source (original): https://github.com/ajinabraham/njsscan/blob/master/tests/assets/node_source/true_positives/semantic_grep/xss/xss_templates.js
// hash: e7a0a61
function name() {
    var x = '<h1>hell0</h1>'
    // ruleid:handlebars_safestring
    var y = new Handlebars.SafeString(x);
    // ruleid:handlebars_safestring
    return new Handlebars.SafeString('<img src="" onload=alert(0)>');
}

function test2() {
    var x = 'foooo'
    var z = new Handlebars;
    // ruleid:handlebars_safestring
    var xx = z.SafeString(x)
    return xx;
}


// ruleid:handlebars_noescape
var template = Handlebars.compile(source, { noEscape: true });
var template = "This is {{target}}";
var target = "user's pictures";
// ruleid:handlebars_noescape
var result = Handlerbars.compile(template, { noEscape: true })({ target: target });
// ruleid:squirrelly_autoescape
Sqrl.autoEscaping(false)
