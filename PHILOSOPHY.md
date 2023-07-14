# SAST rules philosophy

We want GitLab SAST to be an easy-to-adopt layer of security that fits in well with existing DevOps and DevSecOps workflows.

This goal, and GitLab's overall [product principles](https://about.gitlab.com/handbook/product/product-principles/), lead to certain guiding principles:
- We emphasize the **default configuration**. We don't want security or development teams to have to figure out customizations before they get value out of SAST.
- We focus on **security**. Issues reported by GitLab SAST should relate to potential security vulnerabilities or weaknesses. Style suggestions or non-security findings should not be included in GitLab SAST rulesets.
- We seek **precision over recall**. Rules that produce a large number of false-positive results should be improved; if improvement is not possible, they should be disabled or removed. While this may lead to false-negative results, it is important that we respect the time and attention of all the humans involved in reviewing SAST results, including both development and security professionals. Too many false-positive results drown out any true-positive findings.