require 'yaml'

# notes
# bandit - do last
# find_sec_bugs_scala - manually do resr
# find_sec_bugs - manually do the rest
# flawfinder is a mess


Dir.glob('mappings/*.yml').each do |mapping_file|
  ruleset = File.basename(mapping_file, '.yml')
  mappings = YAML.safe_load(File.read(mapping_file))
  mappings[ruleset]['mappings'].each do |mapping|

    mapping['rules'].each_with_index do |rule, idx|
      next if rule.key? 'primary_id'
      primary_id = "#{ruleset}.#{mapping['id']}"
      primary_id = "#{ruleset}.#{mapping['id']}" if mapping['rules'].one?
      primary_id = "#{ruleset}.#{mapping['id']}-#{idx+1}" if ['find_sec_bugs', 'gosec', 'security_code_scan'].include? ruleset
      primary_id = primary_id.gsub(/_scala/, '') if ruleset == 'find_sec_bugs_scala'
      rule['primary_id'] = primary_id
    end
  end

  # Write the new primary IDs
  File.open(mapping_file, 'w') do |file|
    file.write(mappings.to_yaml)
  end

  # fix the formatting
  mappings = File.read(mapping_file)
  mappings.gsub!(/: ([^"].+)$/, ': "\1"')
  File.open(mapping_file, 'w') do |file|
    file.write(mappings)
  end
end