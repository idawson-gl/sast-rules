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

Dir.glob('mappings/*.yml').each do |mapping_file|
  prefix = File.basename(mapping_file, '.yml')

  dict = YAML.safe_load(File.read(mapping_file))

  native_id = dict[prefix]['native_id']
  analyzer = dict[prefix].fetch('native_analyzer', prefix)

  rulez = {}
  id2rules = {}
  rule2ids = {}
  rule_file_paths = []

  dict[prefix]['mappings'].each do |mapping|
    id = mapping['id']
    id2rules[id] = [] unless id2rules.key?(id)
    mapping['rules'].each do |rule|
      rule_path = rule['path']
      rule2ids[rule_path] = [] unless rule2ids.key?(rule_path)
      rule2ids[rule_path] << id
      id2rules[id] << rule_path
      rule_file_paths << rule_path
    end
  end

  rule_file_paths.sort!.uniq!

  rule_file_paths.each do |rule_file_path|
    rule_file = YAML.safe_load(File.read("#{rule_file_path}.yml"))

    # there's only ever one rule in a rule file
    rule = rule_file['rules'].first

    ids = rule2ids[rule_file_path]

    # generate a unique rule hash that makes it possible to map results
    # back to the original analyzer -- note that we have n:m mappings multiple
    # native ids can be mapped to a collection of semgrep rules and vice versa
    # every rule gets coordinates: original_rule_id-array index number
    suffix = ids.map { |id| "#{id}-#{id2rules[id].find_index(rule_file_path) + 1}" }.join('.')
    newid = "#{analyzer}.#{suffix}"
    rule['id'] = newid
    rulez[newid] = rule
    secondary_ids = []
    ids.uniq.each do |id|
      secondary_ids << {
        'name' => native_id['name'].gsub('$ID', id),
        'type' => native_id['type'].gsub('$ID', id),
        'value' => native_id['value'].gsub('$ID', id)
      }
    end
    primary_id = ids.one? ? newid.delete_suffix('-1') : newid
    primary_id = newid if ['flawfinder', 'gosec', 'security_code_scan', 'find_sec_bugs'].include? prefix
    primary_id = rule['primary_id'] if rule.key? 'primary_id'
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
