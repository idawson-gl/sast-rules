require 'yaml'

Dir.glob('mappings/*.yml').each do |mapping_file|
  ruleset = File.basename(mapping_file, '.yml')
  mappings = YAML.safe_load(File.read(mapping_file))
  mappings[ruleset]['mappings'].each do |mapping|

    mapping['rules'].each_with_index do |rule, idx|
      next if rule.key? 'primary_id'
      primary_id = "#{ruleset}.#{mapping['id']}-#{idx+1}"
      primary_id = "#{ruleset}.#{mapping['id']}" if mapping['rules'].one?
      rule['primary_id'] = primary_id
    end
  end

  # Write the new primary IDs
  File.open(mapping_file, 'w') do |file|
    file.write(mappings.to_yaml)
  end

  # fix the formatting
  mappings = File.read(mapping_file)
  pp mappings
  mappings = mappings.gsub(/: ([^"].+)/, ": \"\1\"")
  File.open(mapping_file, 'w') do |file|
    file.write(mappings)
  end

  # pp mappings

  break
end