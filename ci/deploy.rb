require 'yaml'
require 'json'
require_relative './autoformat.rb'

DESTINATION_DIR = 'dist'

def main(version)

  Dir.glob('mappings/*.yml').each do |mapping_file|
    ruleset = File.basename(mapping_file, '.yml')
    mappings = YAML.safe_load(File.read(mapping_file))

    generated_rules = []
    mappings[ruleset]['mappings'].each do |rule_mapping|
      mapping_rule_data = rule_mapping['rules'].first

      parsed_rule = YAML.safe_load(File.read("#{mapping_rule_data['path']}.yml"))['rules'].first()
      parsed_rule['id'] = mapping_rule_data['id']
      parsed_rule['metadata']['primary_identifier'] = mapping_rule_data['primary_id']
      # parsed_rule['metadata']['secondard_identifiers'] = generate_secondary_identifiers()
      generated_rules.append(parsed_rule)
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

# def generate_secondary_identifiers()
#   secondary_identifiers = []
#   ids = rule2ids[rule_file_path]
#   ids.uniq.each do |id|
#     secondary_ids << {
#       'name' => native_id['name'].gsub('$ID', id),
#       'type' => native_id['type'].gsub('$ID', id),
#       'value' => native_id['value'].gsub('$ID', id)
#     }
#   end
# end

main(ARGV[0])
