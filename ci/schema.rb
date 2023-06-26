#!/usr/bin/env ruby
# frozen_string_literal: true

require 'yaml'
require 'json'
require 'json-schema'
require 'optparse'

current_dir = File.dirname(__FILE__)
schema_file = File.join(current_dir, 'schema.json')
$schema = JSON.parse(File.read(schema_file))

def validate_mutually_exclusive_versions(yaml_dict)
  errors = []

  raw_fixed = (yaml_dict['fixed_versions'] || []).map(&:to_s)
  raw_affected = yaml_dict['affected_range']

  # e.g. maven/org.apache.thrift/ABCD
  package_type_str = yaml_dict['package_slug'].split('/').first

  raw_fixed.each do |raw_fixed_str|
    sat = SemverDialects::VersionChecker.version_sat?(package_type_str, raw_fixed_str, raw_affected)
    errors << "Fixed version #{raw_fixed_str} may be also be affected (#{raw_affected})" if sat
  end

  errors
end

def validate_short_description_or_cwe(yaml_dict)
  errors = []
  cwe = yaml_dict["rules"][0]["metadata"]["cwe"]
  short = yaml_dict["rules"][0]["metadata"]["shortDescription"]

  if cwe.nil?
    return errors
  end

  if (!cwe.include?(":") and short.nil?)
    errors << "#{yaml_dict["rules"][0]["id"]} is missing CWE based title (CWE-XXX: title) or shortDescription title"
  end
  errors
end

def validation_errors(yaml_file, semantic = false)
  errors = []
  begin
    yaml_dict = YAML.load_file(yaml_file)
    errors = JSON::Validator.fully_validate($schema, yaml_dict.to_json)
    errors += validate_short_description_or_cwe(yaml_dict)
    errors += validate_semantics(yaml_dict) if semantic
  rescue StandardError => e
    errors << e.message
  end
  errors
end

def obtain_yaml_files(path)
  File.directory?(path) ? Dir.glob("#{path}/**/*.yml") : [path]
end

options = {
  semantic: false
}
optparse = OptionParser.new do |opts|
  opts.banner = "#{$PROGRAM_NAME} <path>"
  opts.on('-h', '--help', 'Print this help') do
    puts opts
    puts "\nPath can be a yaml file and/or a directory"
    exit(1)
  end
end
optparse.parse!(into: options)

if ARGV.empty?
  puts "At least one file/directory is required\n"
  puts optparse.help
  exit(1)
end

# ensure all provided paths exist
if ARGV.reject { |path| File.exist?(path) }.any?
  puts "Not all provided paths exist\n\n"
  puts optparse.help
  exit(1)
end

num_errors = 0
ARGV.flat_map { |path| obtain_yaml_files(path) }.each do |yaml_file|
  next if %w[bandit eslint flawfinder gosec find_sec_bugs find_sec_bugs_scala nodejs_scan security_code_scan scaffold gitlab_javascript].include?(File.basename(yaml_file, '.yml'))

  errors = validation_errors(yaml_file)
  next unless errors.any?

  num_errors += errors.length
  puts "#{yaml_file} is invalid:"
  errors.each do |error|
    puts "    - #{error}"
  end
end

if num_errors.positive?
  puts "\n\n#{num_errors} were detected"
  exit(1)
end

puts 'All yaml files are valid'
exit(0)
