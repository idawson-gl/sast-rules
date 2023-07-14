# SAST rules

This repository holds the rules used in the [Semgrep-based GitLab SAST analyzer](https://gitlab.com/gitlab-org/security-products/analyzers/semgrep).

GitLab writes, maintains, tests, and supports these rules.
They're available by default in GitLab SAST.

The GitLab Vulnerability Research team manages changes to these rules in collaboration with the GitLab Static Analysis team.

If you have questions about GitLab SAST or these rules, you can:
- Check the [GitLab SAST documentation](https://docs.gitlab.com/ee/user/application_security/sast/), including how to [customize rulesets](https://docs.gitlab.com/ee/user/application_security/sast/customize_rulesets.html).
- Request help from [GitLab Support](https://about.gitlab.com/support/) or your account team.
- [File an issue in `gitlab-org/gitlab`](https://gitlab.com/gitlab-org/gitlab/-/issues/?sort=created_date&state=opened&first_page_size=100).
     - To ensure your issue lands in the right queue, write the following comment after creating your issue: `@gitlab-bot label ~"group::vulnerability research" ~"Category:SAST"`

## Everyone can contribute

We welcome contributions to this project.
You can [create an MR directly](https://gitlab.com/gitlab-org/security-products/sast-rules/-/merge_requests) to update rule logic or metadata.
You should update individual rule files, not the automatically-generated files in the `dist/` directory.

Because these rules affect the default configuration of all GitLab SAST users, some changes may not be accepted right away.
For example, changes to severity values or reorganizations to existing rules may require additional coordination to avoid disrupting customer workflows.
Before creating an MR, you can optionally [open an issue in gitlab-org/gitlab](https://gitlab.com/gitlab-org/gitlab/-/issues) to discuss your proposed approach and understand if it's likely to be accepted.
After you file your issue, please write the following comment to put it in the right queue: `@gitlab-bot label ~"group::vulnerability research" ~"Category:SAST"`.

### Credits

GitLab gratefully acknowledges the following contributors from the wider community for their valuable contributions.

| Author            | MRs/Issues |
| ----------------- | ---------- |
| @masakura         | !99, !107  |
| @gregory.mcdaniel | #32        |
| @niklas.volcz.    | !183       |

### Contribution instructions

After making changes to rules or mappings, make sure to run `./ci/deploy.sh <semantic version>`
and commit your updates to the `/dist` directory where `<semantic version>`
should correspond to the latest published version in [CHANGELOG.md](./CHANGELOG.md)>

### Versioning and Changelog

We apply the following semantic versioning scheme to this repository:

1. patch version increment: for updated/patched/added rules.
1. minor version increment: backwards-compatible YAML schema changes (e.g., adding/removing optional fields).
1. major version increment: non-backwards-compatible YAML schema changes (e.g., adding/removing required fields)

## Related projects and resources

- [GitLab SAST rule philosophy](./PHILOSOPHY.md)
- [GitLab SAST documentation](https://docs.gitlab.com/ee/user/application_security/sast/)
- How to [customize rulesets](https://docs.gitlab.com/ee/user/application_security/sast/customize_rulesets.html) used in the Semgrep-based GitLab SAST analyzer.
- [Rule testing project](https://gitlab.com/gitlab-org/security-products/sast-rule-testing-framework/rule-testing)

## Technical details

### Testing

We follow the testing methodology laid out in this [blog post](https://about.gitlab.com/blog/2021/09/08/write-vulnerability-detection-rules/).

### Repository layout

The repository is structured as described below:

``` bash
.
├── mappings
│   └── analyzer.yml
├── dist
│   └── pack.yml
├── c
│   ├── buffer
│   │   ├── rule-strcpy.yml
│   │   ├── test-strcpy.c
│   │   ├── rule-memcpy.yml
│   │   └── test-memcpy.c
│   └── ...
└── javascript
│   └── ...
└── python
│    ├── assert
│    │   ├── rule-assert.yml
│    │   └── test-assert.py
│    └── exec
│    │   ├── rule-exec.yml
│    │   ├── test-exec.yml
│    │   ├── rule-something.yml
│    │   └── test-something.yml
│    └── permission
│    │   ├── rule-chmod.yml
│    │   └── test-chmod.py
│    └── ...
└── ...
```

The structure above follows the pattern:
`<language>/<ruleclass>/{rule-<rulename>.yml, test-<rulename>\..*}` where
`language` denotes the target programming language, `<ruleclass>` is a
descriptive name for the class of issues the rule aims to detect and
`<rulename>` is a descriptive name for the actual rule.

We can have multiple test cases per rule (all prefixed with `test-`) and rule
files `rule-<rulename>.yml` that are prefixed with `rule-`; a rule file
contains a single Semgrep rule.

The `mappings` and `dist` directories include the rule-pack configuration which
define the rules that should included into rule-packs and the resulting,
assembled rule-packs.

### Formatting guidelines

Rules contained in this repository have to adhere to the following format:

- Use `"` for strings, otherwise the YAML literal block |
- No collapsing of array elements
- max line-length/text-width: 100 characters
- indentation: 2 spaces
- every rule has to have a corresponding test-case
- if provided, comments-section at the top of the rule file
- every YAML files starts with `---`

The script `ci/autoformat.rb` automatically formats/rewrites all the rules files
so that they adhere to our guidelines listed above. It can be
executed by running `ci/autoformat.rb` within the sast-rules directory after
installing the gems `psych yaml fileutils` with `gem install psych yaml fileutils`.

### Mappings

The mappings directory in this repository contains YAML configuration files
that map native analyzer ids to the corresponding Semgrep rules. These mappings
are digested by the [testing framework](https://gitlab.com/gitlab-org/security-products/sast-rule-testing-framework/rule-testing)
to perform an automated gap analysis; the goal of this analysis is to check
whether there is an unexpected deviation between Semgrep (with the rules in this repository)
and a given analyzer.

In addition to that mappings are also used to automatically assemble
rule-packs. The snippet below illustrates an example mapping files for the
`bandit` analyzer. The `native_id` section includes some information about the
native id mappings. The actual rule mappings are defined in the `mappings`
section. Each mapping defines of which Semgrep rules in this repository, a
bandit rules is composed. Note that the order of the rules in the files are
listed does matter at the moment, so that new mappings should be appended at
the end.

``` yaml
bandit:
  native_id:
    type: "bandit_test_id"
    name: "Bandit Test ID: $ID"
    value: "$ID"
  mappings:
  - id: "B301"
    rules:
      - "python/deserialization/rule-cpickle"
      - "python/deserialization/rule-shelve"
      - "python/deserialization/rule-pickle"
      - "python/deserialization/rule-dill"
  - id: "B101"
  # ...
```

### Data sources

The rules and test-cases in this repository are partially sourced from the
sources listed below:
1. https://github.com/returntocorp/semgrep-rules
1. https://github.com/PyCQA/bandit
1. https://github.com/nodesecurity/eslint-plugin-security
1. https://github.com/jsx-eslint/eslint-plugin-react
1. https://github.com/david-a-wheeler/flawfinder/blob/master/flawfinder.py

The details are listed in the headers of all the rule end test-files including
the licensing information and proper attribution.

## Rules that are not covered at the moment

### Bandit

#### Excluded patterns (1)
- B308: [django.utils.safestring.mark_safe](https://bandit.readthedocs.io/en/1.7.1/blacklists/blacklist_calls.html#b308-mark-safe) This rule is basically redundant with [B703](https://bandit.readthedocs.io/en/latest/plugins/b703_django_mark_safe.html)
- B109: [password_config_option_not_marked_secret](https://bandit.readthedocs.io/en/latest/plugins/b109_password_config_option_not_marked_secret.html) Not supported anymore since the plugin was removed
- B111: [execute_with_run_as_root_equals_true](https://bandit.readthedocs.io/en/latest/plugins/b111_execute_with_run_as_root_equals_true.html) Not supported anymore since the plugin was removed
- B322: [input](https://bandit.readthedocs.io/en/latest/blacklists/blacklist_calls.html#b322-input) Not supported anymore since the plugin was removed
- B414: [import_pycryptodome](https://bandit.readthedocs.io/en/latest/blacklists/blacklist_imports.html#b414-import-pycryptodome) Not supported anymore since the plugin was removed


#### Adjusted patterns (3)
- B503: [ssl_with_bad_defaults](https://bandit.readthedocs.io/en/latest/plugins/b503_ssl_with_bad_defaults.html) Our Semgrep pattern captures both B503 and B502 because they are very similar and are both practically capturing insecure setting using outdated versions of encryption algorithms.
- B110: [try_except_pass](https://bandit.readthedocs.io/en/latest/plugins/b110_try_except_pass.html) The Semgrep rule checks the whole try except block whereas bandit reports every except case. The Semgrep rule approximates the original rule behaviour looking at various permutations of except pass cases embedded in a try ... except block.
- B112: [try_except_continue](https://bandit.readthedocs.io/en/latest/plugins/b112_try_except_continue.html) The Semgrep rule checks the whole try except block whereas bandit reports every except case. The Semgrep rule approximates the original rule behaviour looking at various permutations of except continue cases embedded in a try ... except block.

### ESLint

#### Patterns we were unable to migrate (1)

- `detect-unsafe-regex`: [Detects potentially unsafe regular expressions, which may take a very long time to run, blocking the event loop](https://github.com/eslint-community/eslint-plugin-security/blob/main/docs/rules/detect-unsafe-regex.md): This problem is solved by applying set of conditional logic on each character of a target string. This cannot be accomplished in Semgrep.


### Gosec

#### Patterns we were unable to migrate (2)

- G104: [Metavariable types not supported for go at the moment](https://github.com/returntocorp/semgrep-rules/issues/1149)
- G307: [Deferring a method which returns an error](https://github.com/returntocorp/semgrep-rules/issues/1149)

### find-sec-bugs

Java, Scala

#### Adjusted patterns

| Rule ID                                 | Description                                                                                                                                                                                                                  | Status | Comment               |
| --------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------ | --------------------- |
| `HARD_CODE_PASSWORD`              | [Hardcoded Password (Scala)](https://find-sec-bugs.github.io/bugs.html#HARD_CODE_PASSWORD)                                                                                                      | :x:    |  The behaviour is not completely on par with find-sec-bugs; we excluded some patterns that are prone to FPs.  |

#### Out of scope patterns (25)

Out of scope patterns w.r.t. https://gitlab.com/gitlab-org/gitlab/-/issues/354762#rules-with-completion-status are all those patterns that are unrelated to Java.

| Rule ID                                 | Description                                                                                                                                                                                                                  | Status | Comment               |
| --------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------ | --------------------- |
| `PREDICTABLE_RANDOM_SCALA`              | [Predictable pseudorandom number generator (Scala)](https://find-sec-bugs.github.io/bugs.html#PREDICTABLE_RANDOM_SCALA)                                                                                                      | :x:    | Scala not supported   |
| `SCALA_COMMAND_INJECTION`               | [Potential Command Injection (Scala)](https://find-sec-bugs.github.io/bugs.html#SCALA_COMMAND_INJECTION)                                                                                                                     | :x:    | Scala not supported   |
| `SCALA_PATH_TRAVERSAL_IN`               | [Potential Path Traversal using Scala API (file read)](https://find-sec-bugs.github.io/bugs.html#SCALA_PATH_TRAVERSAL_IN)                                                                                                    | :x:    | Scala not supported   |
| `SCALA_PLAY_SSRF`                       | [Scala Play Server-Side Request Forgery (SSRF)](https://find-sec-bugs.github.io/bugs.html#SCALA_PLAY_SSRF)                                                                                                                   | :x:    | Scala not supported   |
| `SCALA_SENSITIVE_DATA_EXPOSURE`         | [Potential information leakage in Scala Play](https://find-sec-bugs.github.io/bugs.html#SCALA_SENSITIVE_DATA_EXPOSURE)                                                                                                       | :x:    | Scala not supported   |
| `SCALA_SQL_INJECTION_ANORM`             | [Potential Scala Anorm Injection](https://find-sec-bugs.github.io/bugs.html#SCALA_SQL_INJECTION_ANORM)                                                                                                                       | :x:    | Scala not supported   |
| `SCALA_SQL_INJECTION_SLICK`             | [Potential Scala Slick Injection](https://find-sec-bugs.github.io/bugs.html#SCALA_SQL_INJECTION_SLICK)                                                                                                                       | :x:    | Scala not supported   |
| `SCALA_XSS_MVC_API`                     | [Potential XSS in Scala MVC API engine](https://find-sec-bugs.github.io/bugs.html#SCALA_XSS_MVC_API)                                                                                                                         | :x:    | Scala not supported   |
| `SCALA_XSS_TWIRL`                       | [Potential XSS in Scala Twirl template engine](https://find-sec-bugs.github.io/bugs.html#SCALA_XSS_TWIRL)                                                                                                                    | :x:    | Scala not supported   |
| `PLAY_UNVALIDATED_REDIRECT`             | [Unvalidated Redirect (Play Framework)](https://find-sec-bugs.github.io/bugs.html#PLAY_UNVALIDATED_REDIRECT)                                                                                                                 | :x:    | Scala not supported   |
| `ANDROID_BROADCAST`                     | [Broadcast (Android)](https://find-sec-bugs.github.io/bugs.html#ANDROID_BROADCAST)                                                                                                                                           | :x:    | Android not supported |
| `ANDROID_EXTERNAL_FILE_ACCESS`          | [External file access (Android)](https://find-sec-bugs.github.io/bugs.html#ANDROID_EXTERNAL_FILE_ACCESS)                                                                                                                     | :x:    | Android not supported |
| `ANDROID_GEOLOCATION`                   | [WebView with geolocation activated (Android)](https://find-sec-bugs.github.io/bugs.html#ANDROID_GEOLOCATION)                                                                                                                | :x:    | Android not supported |
| `ANDROID_WEB_VIEW_JAVASCRIPT_INTERFACE` | [WebView with JavaScript interface (Android)](https://find-sec-bugs.github.io/bugs.html#ANDROID_WEB_VIEW_JAVASCRIPT_INTERFACE)                                                                                               | :x:    | Android not supported |
| `ANDROID_WEB_VIEW_JAVASCRIPT`           | [WebView with JavaScript enabled (Android)](https://find-sec-bugs.github.io/bugs.html#ANDROID_WEB_VIEW_JAVASCRIPT)                                                                                                           | :x:    | Android not supported |
| `ANDROID_WORLD_WRITABLE`                | [World writable file (Android)](https://find-sec-bugs.github.io/bugs.html#ANDROID_WORLD_WRITABLE)                                                                                                                            | :x:    | Android not supported |
| `SQL_INJECTION_ANDROID`                 | [Potential Android SQL Injection](https://find-sec-bugs.github.io/bugs.html#SQL_INJECTION_ANDROID)                                                                                                                           | :x:    | Android not supported |
| `GROOVY_SHELL`                          | [Potential code injection when using GroovyShell](https://find-sec-bugs.github.io/bugs.html#GROOVY_SHELL)                                                                                                                    | :x:    | Groovy not supported  |
| `JSP_INCLUDE`                           | [Dynamic JSP inclusion](https://find-sec-bugs.github.io/bugs.html#JSP_INCLUDE)                                                                                                                                               | :x:    | JSP not supported     |
| `JSP_JSTL_OUT`                          | [Escaping of special XML characters is disabled](https://find-sec-bugs.github.io/bugs.html#JSP_JSTL_OUT)                                                                                                                     | :x:    | JSP not supported     |
| `JSP_SPRING_EVAL`                       | [Dynamic variable in Spring expression](https://find-sec-bugs.github.io/bugs.html#JSP_SPRING_EVAL)                                                                                                                           | :x:    | JSP not supported     |
| `JSP_XSLT`                              | [A malicious XSLT could be provided to the JSP tag](https://find-sec-bugs.github.io/bugs.html#JSP_XSLT)                                                                                                                      | :x:    | JSP not supported     |
| `XSS_JSP_PRINT`                         | [Potential XSS in JSP](https://find-sec-bugs.github.io/bugs.html#XSS_JSP_PRINT)                                                                                                                                              | :x:    | JSP not supported     |
| `XSS_REQUEST_PARAMETER_TO_JSP_WRITER`   | [XSS: Servlet reflected cross site scripting vulnerability](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html#xss-servlet-reflected-cross-site-scripting-vulnerability-xss-request-parameter-to-servlet-writer) | :x:    | JSP not supported     |
| `REQUESTDISPATCHER_FILE_DISCLOSURE`     | [RequestDispatcher File Disclosure](https://find-sec-bugs.github.io/bugs.html#REQUESTDISPATCHER_FILE_DISCLOSURE)                                                                                                             | :x:    | JSP not supported     |

#### Excluded patterns (6)

We excluded the patterns below because they are overly verbose; they are triggered by existing entry-points and do not indicate any vulnerability.

| Rule ID             | Description                                                                            | Status          | Comment                                                                                                                             |
| ------------------- | -------------------------------------------------------------------------------------- | --------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| `STRUTS1_ENDPOINT`  | [Found Struts 1 endpoint](https://find-sec-bugs.github.io/bugs.html#STRUTS1_ENDPOINT)  | :no_entry_sign: | the endpoint rules only provide general information about potential security issue which seems noisy -- I think we can skip them    |
| `STRUTS2_ENDPOINT`  | [Found Struts 2 endpoint](https://find-sec-bugs.github.io/bugs.html#STRUTS2_ENDPOINT)  | :no_entry_sign: | the endpoint rules only provide general information about potential security issue which seems noisy -- I think we can skip them    |
| `SPRING_ENDPOINT`   | [Found Spring endpoint](https://find-sec-bugs.github.io/bugs.html#SPRING_ENDPOINT)     | :no_entry_sign: | We cannot cope with annotations; in addition endpoints should probably not end up in the final security report anyway               |
| `TAPESTRY_ENDPOINT` | [Found Tapestry page](https://find-sec-bugs.github.io/bugs.html#TAPESTRY_ENDPOINT)     | :no_entry_sign: | We cannot cope with annotations; in addition endpoints should probably not end up in the final security report anyway.              |
| `JAXRS_ENDPOINT`    | [Found JAX-RS REST endpoint](https://find-sec-bugs.github.io/bugs.html#JAXRS_ENDPOINT) | :no_entry_sign: | the endpoint rules only provide general information about potential security issue which seems noisy -- I think we can skip them    |
| `JAXWS_ENDPOINT`    | [Found JAX-WS SOAP endpoint](https://find-sec-bugs.github.io/bugs.html#JAXWS_ENDPOINT) | :no_entry_sign: | the endpoint rules only provide general information about potential security issue which seems noisy -- I think we can skip them    |
| `HARD_CODE_KEY`     | Secret detection rule                                                                  | :no_entry_sign: | Secret Detection is taken care of by a dedicated [analyzer](https://docs.gitlab.com/ee/user/application_security/secret_detection/) |

#### Patterns we were unable to migrate (12)

The patterns below could not be migrated, because they required features not supported by Semgrep. See https://gitlab.com/gitlab-org/gitlab/-/issues/357679 for more information.

| Rule ID                                    | Description                                                                                                                   | Status          | Comment                                                                                                                                                                                                                                 |
| ------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------- | --------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `SPRING_CSRF_UNRESTRICTED_REQUEST_MAPPING` | [Spring CSRF unrestricted RequestMapping](https://find-sec-bugs.github.io/bugs.html#SPRING_CSRF_UNRESTRICTED_REQUEST_MAPPING) | :no_entry_sign: | No support for parsing annotations                                                                                                                                                                                                      |
| `SPRING_UNVALIDATED_REDIRECT`              | [Spring Unvalidated Redirect](https://find-sec-bugs.github.io/bugs.html#SPRING_UNVALIDATED_REDIRECT)                          | :no_entry_sign: | No support for annotations                                                                                                                                                                                                              |
| `WICKET_ENDPOINT`                          | [Found Wicket WebPage](https://find-sec-bugs.github.io/bugs.html#WICKET_ENDPOINT)                                             | :no_entry_sign: | the endpoint rules only provide general information about potential security issue which seems noisy -- I think we can skip them                                                                                                        |
| `UNSAFE_HASH_EQUALS`                       | [Unsafe hash equals](https://find-sec-bugs.github.io/bugs.html#UNSAFE_HASH_EQUALS)                                            | :no_entry_sign: | this rule is highly prone to FPs -- it checks for unsecure hash functions by looking for keywords (e.g., sha) in variable or parameter names. As we are already covered by secret detection, we can probably omit this particular rule. |
| `STATIC_IV`                                | [Static IV](https://find-sec-bugs.github.io/bugs.html#STATIC_IV)                                                              | :no_entry_sign: | https://gitlab.com/gitlab-org/gitlab/-/issues/357679#note_905023485                                                                                                                                                                     |
| `DESERIALIZATION_GADGET`                   | [This class could be used as deserialization gadget](https://find-sec-bugs.github.io/bugs.html#DESERIALIZATION_GADGET)        | :no_entry_sign: | Multiple logical flows involved. Cannot be achieved in Semgrep.                                                                                                                                                                         |
| `ENTITY_LEAK`                              | [Unexpected property leak](https://find-sec-bugs.github.io/bugs.html#ENTITY_LEAK)                                             | :no_entry_sign: | Annotations of classes are processed to determine the result. This cannot be achieved in Semgrep.                                                                                                                                       |
| `ENTITY_MASS_ASSIGNMENT`                   | [Mass assignment](https://find-sec-bugs.github.io/bugs.html#ENTITY_MASS_ASSIGNMENT)                                           | :no_entry_sign: | Annotations of classes are processed to determine the result. This cannot be achieved in Semgrep.                                                                                                                                       |
| `ESAPI_ENCRYPTOR`                          | [Use of ESAPI Encryptor](https://find-sec-bugs.github.io/bugs.html#ESAPI_ENCRYPTOR)                                           | :no_entry_sign: | Config files related. We currently support only files with `.java` extensions.                                                                                                                                                          |
| `JACKSON_UNSAFE_DESERIALIZATION`           | [Unsafe Jackson deserialization configuration](https://find-sec-bugs.github.io/bugs.html#JACKSON_UNSAFE_DESERIALIZATION)      | :no_entry_sign: | [Reason](https://gitlab.com/gitlab-org/gitlab/-/issues/357679#note_905594086)                                                                                                                                                           |
| `OBJECT_DESERIALIZATION`                   | [Object deserialization is used](https://find-sec-bugs.github.io/bugs.html#OBJECT_DESERIALIZATION)                            | :no_entry_sign: | This problem is solved by determining Interface supersets and Annotation metadata. This cannot be accomplished in Semgrep                                                                                                               |
| `REDOS`                                    | [Regex DOS (ReDOS)](https://find-sec-bugs.github.io/bugs.html#REDOS)                                                          | :no_entry_sign: | This problem is solved by applying set of conditional logic on each character of a target string. This cannot be accomplished in Semgrep                                                                                                |

### security-code-scan

#### Modified patterns (1)

| Rule ID   | Description                                                     | Comment                                                                                                                |
| --------- | --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| `SCS0018` | [Path Traversal](https://security-code-scan.github.io/#SCS0018) | We adapted the pattern to not cover arguments passed to `Main` as sources because this often lead to FPs for CLI apps. |

#### Excluded patterns (1)

We excluded the patterns below because they are overly verbose.

| Rule ID   | Description                                                         | Status          | Comment                                                                                                                |
| --------- | ------------------------------------------------------------------- | --------------- | ---------------------------------------------------------------------------------------------------------------------- |
| `SCS0015` | [Hardcoded Password](https://security-code-scan.github.io/#SCS0015) | :no_entry_sign: | This is better served by Secrets Detection as there are a multitude of ways that hardcoded passwords can be specified. |

#### Patterns we were unable to migrate (5)

The patterns below could not be migrated, because they required features not supported by Semgrep.

| Rule ID   | Description                                                                                                        | Status          | Comment                                                                                                                                                                                                                                 |
| --------- | ------------------------------------------------------------------------------------------------------------------ | --------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `SCS0021` | [Request Validation Disabled (Configuration File)](https://security-code-scan.github.io/#SCS0021)                  | :no_entry_sign: | XML configuration file.                                                                                                                                                                                                                 |
| `SCS0022` | [Event Validation Disabled](https://security-code-scan.github.io/#SCS0022)                                         | :no_entry_sign: | XML configuration file.                                                                                                                                                                                                                 |
| `SCS0023` | [View State Not Encrypted](https://security-code-scan.github.io/#ViewState)                                        | :no_entry_sign: | XML configuration file.                                                                                                                                                                                                                 |
| `SCS0024` | [View State MAC Disabled](https://security-code-scan.github.io/#SCS0024)                                           | :no_entry_sign: | XML configuration file.                                                                                                                                                                                                                 |
| `SCS0008` | [Cookie Without SSL Flag](https://security-code-scan.github.io/#SCS0008)                                           | :no_entry_sign: | The SCS rule also detects vulnerabilities in ASP.NET config files which is not supported by Semgrep. We also haven't been able to detect these with SCS within the `gapanalysis` job as the `HttpCookie` class requires .NET Framework. |
| `SCS0009` | [Cookie Without HttpOnly Flag](https://security-code-scan.github.io/#SCS0009)                                      | :no_entry_sign: | The SCS rule also detects vulnerabilities in ASP.NET config files which is not supported by Semgrep. We also haven't been able to detect these with SCS within the `gapanalysis` job as the `HttpCookie` class requires .NET Framework. |
| `SCS0002` | [SQL Injection](https://security-code-scan.github.io/#SCS0002)                                                     | :no_entry_sign: | The SCS rule also detects vulnerabilities in ASP.NET UI code, which Semgrep does not support.                                                                                                                                           |
| `SCS0003` | [XPath Injection](https://security-code-scan.github.io/#SCS0003)                                                   | :no_entry_sign: | The SCS rule also detects vulnerabilities in ASP.NET UI code, which Semgrep does not support.                                                                                                                                           |
| `SCS0003` | [XPath Injection](https://security-code-scan.github.io/#SCS0003)                                                   | :no_entry_sign: | The SCS rule also detects vulnerabilities in ASP.NET UI code, which Semgrep does not support.                                                                                                                                           |
| `SCS0030` | [Request validation is enabled only for pages (Configuration File)](https://security-code-scan.github.io/#SCS0030) | :no_entry_sign: | This rule relates to changes in the Configuration File(XML) format. Semgrep does not have GA support for HTML/XML format.                                                                                                               |

## Rule synchronization from Upstream scanners

Semgrep rules should be kept in-sync with upstream scanners regularly; here's the process:

- Pull the newly added rules from the analyzer's Upstream source (excluding the rules which could not be translated due to Semgrep limitations - see above).
- Translate newly identified rules into Semgrep-equivalent rules
- [Map](https://gitlab.com/gitlab-org/secure/gsoc-sast-vulnerability-rules/playground/sast-rules#mappings) them against native analyzer's IDs in this repository.
- Generate a new ruleset distribution using the instructions described [above](https://gitlab.com/gitlab-org/secure/gsoc-sast-vulnerability-rules/playground/sast-rules#contribution-instructions).
- Add all the un-translatable rules into this file along with the reason against the downstream analyzer/
- Copy over the new ruleset distribution into [`Semgrep/rules`](https://gitlab.com/gitlab-org/security-products/analyzers/semgrep/-/tree/main/rules) to reflect rule changes in the analyzer.

For better tracking purposes, create a dedicated issue on rule synchronization cadence and create a sub-task for each semgrep-translated analyzer. The subtask should contain all the new rules that should be synchronized. Here's an example [issue](https://gitlab.com/gitlab-org/gitlab/-/issues/373117) that has followed the mentioned process.
