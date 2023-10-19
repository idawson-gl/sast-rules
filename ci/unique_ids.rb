#!/usr/bin/env ruby
# frozen_string_literal: true

require 'yaml'

all_ruleids = Dir.glob('**/rule-*.yml').map { |file| YAML.safe_load(File.read(file))['rules'].first['id'] }
# filter duplicate rule ids
duplicates = all_ruleids.tally.filter { |_ruleid, count| count > 1 }.map { |ruleid, _count| ruleid }

if duplicates.any?
  puts("duplicate ids: #{duplicates.join(',')}")
  exit(1)
end

exit(0)
