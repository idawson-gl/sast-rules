#!/usr/bin/env ruby
# This script consolidates all yaml files to a single rule files

require 'yaml'
require 'json'
require_relative './autoformat.rb'

if ARGV.empty?
  puts "please provide version tag"
  exit 1
end

dest = 'dist'
version = ARGV[0]

unless version.match?(/[0-9.]{1,15}/)
  puts 'only semver version strings allowed'
  exit 1
end

Dir.mkdir(dest) unless Dir.exist?(dest)

Dir.glob('mappings/*.yml').each do |mappings|
  next unless mappings == "mappings/bandit.yml"
  prefix = File.basename(mappings, '.yml')

  dict = YAML.safe_load(File.read(mappings))

  native_id = dict[prefix]['native_id']

  rulez = {}
  id2rules = {}
  rule2ids = {}
  ruleids = {}
  rulefiles = []
  id2primaryid = {}

  dict[prefix]['mappings'].each do |mapping|
    id = mapping['id']
    ruleids[id] = ruleids.keys.size unless ruleids.key?(id)
    id2rules[id] = [] unless id2rules.key?(id)
    mapping['rules'].each do |rule|
      rule_path = rule['path']
      rule2ids[rule_path] = [] unless rule2ids.key?(rule_path)
      rule2ids[rule_path] << id
      id2rules[id] << rule_path
      id2primaryid[id] = rule['primary_identifier'] if rule['primary_identifier']
      rulefiles << rule_path
    end
  end

  rulefiles.sort!.uniq!

  rulefiles.each do |rfil|
    rulefiledict = YAML.safe_load(File.read("#{rfil}.yml"))
    rulefromfile = rulefiledict['rules'].first
    ids = rule2ids[rfil]

    primary_id = "#{prefix}.#{ids.first}"
    primary_id = id2primaryid[ids.first] if id2primaryid[ids.first]

    # generate a unique rule hash that makes it possible to map results
    # back to the original analyzer -- note that we have n:m mappings multiple
    # native ids can be mapped to a collection of semgrep rules and vice versa
    # every rule gets coordinates: original_rule_id-array index number
    suffix = ids.map { |id| "#{id}-#{id2rules[id].find_index(rfil) + 1}" }.join('.')
    newid = "#{prefix}.#{suffix}"
    rulefromfile['id'] = newid
    rulez[newid] = rulefromfile
    secondary_ids = []
    ids.uniq.each do |id|
      secondary_ids << {
        'name' => native_id['name'].gsub('$ID', id),
        'type' => native_id['type'].gsub('$ID', id),
        'value' => native_id['value'].gsub('$ID', id)
      }
    end
    rulez[newid]['metadata'].merge!('primary_identifier' => primary_id)
    rulez[newid]['metadata'].merge!('secondary_identifiers' => secondary_ids)
  end

  outdict = { 'rules' => rulez.values }
  formatted = AutoFormat.reformat_yaml('', outdict)

  puts("writing #{prefix}.yml")
  File.open("#{dest}/#{prefix}.yml", 'w') do |file|
    file.puts('# yamllint disable')
    file.puts("# rule-set version: #{version}")
    file.puts('# yamllint enable')
    file.write(formatted)
  end
end
