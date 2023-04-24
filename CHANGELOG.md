sast-rules changelog

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

