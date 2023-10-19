// License: MIT (c) JS Foundation and other contributors, https://js.foundation
// source: https://github.com/eslint-community/eslint-plugin-security/blob/main/test/detect-new-buffer.js
// hash: edd1ae2

"use strict";
import { Buffer } from "node:buffer";

function test(param) {
  const good1 = new Buffer("7468697320697320612074c3a97374", "hex");
  let good2 = new Buffer([0x62, 0x75, 0x66, 0x66, 0x65, 0x72]);
  var good3 = new Buffer(10);
  const good4 = new Buffer(good3);
  const bad = new Buffer(param);
}
