sast-rules changelog

## v1.3.30-bugfix
- Change rule ID format from find_sec_bugs.XYZ to find_sec_bugs_scala.XYZ for Scala rules (!210)

## v1.3.30
- Enhance Python ruleset descriptions and titles (!170)

## v1.3.29
- Improve Go memory aliasing in `G601` (!187)

## v1.3.28
- Enhance Javascript ruleset descriptions and titles (!166)

## v1.3.27
- Update Java `rule-SSRF.yml` to match more cases under `java.net.*` package (!186)
- Add Java rule `rule-WeakTLSProtocolVersion.yml` to detect weak TLS versions (!186)

## v1.3.26
- Update Javascript `rule-non_literal_fs_filename.yml` to only flag on fs modules (!183)

## v1.3.25
- Update Java `rule-SpotbugsPathTraversalAbsolute.yml` to handle getResourceAsStream and getResource (!182)

## v1.3.24
- Remove `-1` from all eslint rule IDs (!177)

## v1.3.23
- Update Java `rule-CommandInjection.yml` to match concatenated strings (!169)
- Update Java `rule-SpelView.yml` to also match `ExpressionParser` interface methods (!169)
- Update Java `rule-XpathInjection.yml` to match actual XPath import path (!169)

## v1.3.22
- Update Java `rule-CommandInjection.yml` with ability to match on String arrays (!168)

## v1.3.21
- Update Java `rule-BlowfishKeySize.yml` to add back missing `metavariable` (!169)
- Update Java rules with minor grammatical fixes (!169)

## v1.3.20
- Enhance Java ruleset descriptions and titles (!144)

## v1.3.19
- Update Primary identifiers for `bandit.B303` and `bandit.B304` so that they match the published rules in semgrep (!165)
- Remove `-1` from bandit ruleset IDs and primary identifiers to match the published rules in semgrep (!165)

## v1.3.18
- Update rules that were missing titles by moving them to shortDescription instead of cwe (!161)

## v1.3.17
- Update Primary identifiers for `bandit.B303` and `bandit.B304` so that they match the published rules in semgrep (!155)

## v1.3.16
- Update Primary identifiers for `bandit.B103` so that they match the published rules in semgrep (!154)

## v1.3.15
- Update primary identifier of `bandit.B108-2` to `bandit.B108-1` (!153)

## v1.3.14
- Find Sec Bugs singular rule IDs should include `-1` (!151)
- Security Code Scan singular rule IDs should include `-1` (!151)

## v1.3.13
- feat: Drop high-FP eslint detect-object-injection rule (!151)

## v1.3.12
- Gosec singular rule IDs should include `-1` (!149)

## v1.3.11
- Flawfinder singular rule IDs should include `-1` (!147)

## v1.3.10
- Fix typos in message of yaml load rule (!145)

## v1.3.9
- Enhance usecase coverage for Scala rules (!142)
- Remove redundant mapping of find_sec_bugs in Scala mapping (!142)
- Introduce `native_analyzer` property in the mappings file and use it for primary ID prefix (!142)

## v1.3.8
- Enhance Go ruleset descriptions and titles (!137)

## v1.3.7
- Revert primary identifier changes in !101 to align identifiers to previously-shipped rules (!138)

## v1.3.6
- Update pattern of avoid PyYAML.load in bandit.b506 (!140)

## v1.3.5
- Update C# SQL Injection with link for more details (!139)

## v1.3.4
- Add `generic_error_disclosure` rule for node.js (!124)

## v1.3.3
- Enhance C# ruleset descriptions and titles (!134)

## v1.3.2
- Update `metadata.owasp` to adhere to the pattern `A{number}:{year}-{Title}` (!136)

## v1.3.1
- Enhance C ruleset descriptions and titles (!128)
- Add shortDescription titles to C rulesets (!128)
- Add valdiation to confirm that either cwe tag contains title, or shortDescription is defined (!128)

## v1.3.0
- Add missing OWASP Top10 2017 Categories to C rulesets (!123)
- Fix java/scala OWASP Categories to include missing numerical identifier (!123)
- Add owasp metadata validation to schema (!123)

## v1.2.8
- Improve B608 to work with control flow (!126)

## v1.2.7
- Fix Bandit B113 positional arguments FPs (!122)

## v1.2.6
- Synchronize bandit upstream rules in the ruleset (!119)

## v1.2.5
- Synchronize new upstream rules in the ruleset (!112)

## v1.2.4
- Adjust bandit severity (!116)

## v1.2.3
- Rule refinements for Go (!115)

## v1.2.2
- Rule refinements for Scala (!113)

## v1.2.1
- Support Oracle, Postgres and MySql in .net (community contribution from @masakura) (!107)

## v1.2.0
- Scala support (!109)

## v1.1.12
- Cover more permutations for try...except.. cases (!106)

## v1.1.11
- Eliminate rules that use the `generic` feature (!105)

## v1.1.10
- Fix Bandit B101 rule coverage (!102)

## v1.1.9
- Remove extra colon in bandit rules (!98)

## v1.1.8
- Use single primary id (!101)

## v1.1.7
- Incorporating feedback to improve bandit rule-set (!88)

## v1.1.6
- C# rule refinement (community contribution from @masakura) (!100)

## v1.1.5
- Eliminate FPs for SQLi rule (!95)

## v1.1.4
- Eliminate FPs for SpotBugs hardcoded password rule (!96)

## v1.1.3
- Include eslint security prefix for secondary identifiers only (!95)

## v1.1.2
- Remove security prefix (!94)

## v1.1.1
- Moving the previous id representation; adding more meta information (!93)

## v1.1.0
- Changing deployment target to `/dist`, incorporate meta-information into
  generated rule-packs, update documentation (!87)

