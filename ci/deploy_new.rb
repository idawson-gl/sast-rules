require 'yaml'
require 'json'
require_relative './autoformat.rb'

DESTINATION_DIR = 'dist'
MAPPINGS_PATH = 'mappings/*.yml'

def main(version)
  rule_lookup = RuleLookup.new(MAPPINGS_PATH)
  package_builder = RulesetPackageBuilder.new(rule_lookup)
  package_builder.run(version, MAPPINGS_PATH, DESTINATION_DIR)
end

class RuleLookup
  def initialize(mappings_path)
    @mappings_path = mappings_path
  end

  def find_all_rule_ids_for_path(path)
    return rule_path_to_id[path]
  end

  def rule_path_to_id
    return @rule_path_to_id if defined? @rule_path_to_id
    @rule_path_to_id = {}
    Dir.glob(@mappings_path).each do |mapping_file|
      ruleset = File.basename(mapping_file, '.yml')
      mappings = YAML.safe_load(File.read(mapping_file))

      mappings[ruleset]['mappings'].each do |rule_mapping|
        rule_mapping['rules'].each do |rule|
          @rule_path_to_id[rule['path']] = [] unless @rule_path_to_id.key? rule['path']
          @rule_path_to_id[rule['path']].append(rule_mapping['id'])
        end
      end
    end
    return @rule_path_to_id
  end
end

class RulesetPackageBuilder

  def initialize(rule_lookup)
    @rule_lookup = rule_lookup
  end

  def run(version, mappings_path, destination_path)
    Dir.glob(mappings_path).each do |mapping_file|
      ruleset = File.basename(mapping_file, '.yml')
      mappings = YAML.safe_load(File.read(mapping_file))
  
      native_id = mappings[ruleset]['native_id']

      generated_rules = []
      sorted_mappings.each do |rule_mapping|
        rule_mapping['rules'].each do |mapping_rule_data|
          parsed_rule = YAML.safe_load(File.read("#{mapping_rule_data['path']}.yml"))['rules'].first()
          parsed_rule['id'] = mapping_rule_data['id']
          parsed_rule['metadata']['primary_identifier'] = mapping_rule_data['primary_id']
          parsed_rule['metadata']['secondary_identifiers'] = generate_secondary_identifiers(mapping_rule_data['path'], native_id)
          generated_rules.append(parsed_rule)
        end
      end
  
      formatted_rule = AutoFormat.reformat_yaml('', { 'rules' => generated_rules })
      write_rule(formatted_rule, ruleset, version)
    end
  end

  def write_rule(rule, ruleset, version)
    File.open("#{DESTINATION_DIR}/#{ruleset}.yml", 'w') do |file|
      file.puts('# yamllint disable')
      file.puts("# rule-set version: #{version}")
      file.puts('# yamllint enable')
      file.write(rule)
    end
  end
  
  def generate_secondary_identifiers(path, native_id)
    secondary_identifiers = []
    rule_ids = @rule_lookup.find_all_rule_ids_for_path(path)
    rule_ids.uniq.each do |id|
      secondary_identifiers << {
        'name' => native_id['name'].gsub('$ID', id),
        'type' => native_id['type'].gsub('$ID', id),
        'value' => native_id['value'].gsub('$ID', id)
      }
    end
  
    return secondary_identifiers
  end

end

main(ARGV[0])
