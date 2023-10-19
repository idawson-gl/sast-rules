#!/usr/bin/env ruby
# frozen_string_literal: true

require 'yaml'

rulez = { 'rules' => [] }

rulefiles = Dir.glob('../**/rule-*.yml').to_a
rulefiles.sort!.uniq!

# Combine all semgrep rules into a single file to speed up the evaluation
rulefiles.each do |rfil|
  rulefiledict = YAML.load(File.read(rfil))
  rulefromfile = rulefiledict['rules'].first
  rulefromfile['id'] = rfil
  rulez['rules'] << rulefromfile
end

File.write('combined.yml', rulez.to_yaml)
