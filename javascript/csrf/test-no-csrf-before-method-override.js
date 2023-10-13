// License: MIT (c) JS Foundation and other contributors, https://js.foundation
// source: https://github.com/nodesecurity/eslint-plugin-security/blob/master/test/detect-no-csrf-before-method-override.js
// hash: edd1ae2
'use strict';

const RuleTester = require('eslint').RuleTester;
const tester = new RuleTester();

const ruleName = 'detect-no-csrf-before-method-override';


tester.run(ruleName, require(`../rules/${ruleName}`), {
  valid: [{ code: 'express.methodOverride();express.csrf()' }],
  invalid: [
    {
      code: 'express.csrf();express.methodOverride()',
      errors: [{ message: 'express.csrf() middleware found before express.methodOverride()' }]
    }
  ]
});
