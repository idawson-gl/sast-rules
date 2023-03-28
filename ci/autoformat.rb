#!/usr/bin/env ruby

require 'psych'
require 'yaml'
require 'fileutils'

module AutoFormat
  def self.wrap(txt, col = 95)
    txt.gsub(/(.{1,#{col}})( +|$\n?)|(.{1,#{col}}(?<=\s))/,
               "\\1\\3\n").gsub(/^ *$/, '') # allow long (urls) to not be split
  end

  def self.reformat_yaml(id, yaml_dict)
    ast = Psych.parse_stream(Psych.dump(yaml_dict))

    # apply double quotes
    ast.grep(Psych::Nodes::Scalar).each do |node|
      next if node.value == "regex"
      
      node.plain  = false
      node.quoted = true
      node.style  = Psych::Nodes::Scalar::DOUBLE_QUOTED
    end

    # remove double quotes from keys and adjust
    # message, pattern-inside and pattern
    ast.grep(Psych::Nodes::Mapping).each do |node|
      node.children.each_slice(2) do |k, v|
        next if k.value == "regex" 

        k.plain  = true
        k.quoted = false
        k.style  = Psych::Nodes::Scalar::ANY

        case k.value
        when 'id'
          v.quoted = true
          v.value = id if id != ''
        when 'base'
          v.quoted = false
          v.plain = true
          v.style = Psych::Nodes::Scalar::ANY
        when 'message'
          v.quoted = true
          v.plain = true
          v.style = Psych::Nodes::Scalar::LITERAL
          v.value = AutoFormat.wrap(v.value)
        when 'pattern', 'pattern-not'
          v.style = if v.value.count("\n").zero?
                      Psych::Nodes::Scalar::DOUBLE_QUOTED
                    else
                      Psych::Nodes::Scalar::LITERAL
                    end
        when 'pattern-inside', 'pattern-not-inside'
          v.style = Psych::Nodes::Scalar::LITERAL
        end
      end
    end
    ast.to_yaml
  end

  def self.run
    changed = 0
    Dir.glob('**/rule-*.yml').each do |file|
      id = File.join(File.dirname(file), File.basename(file, '.yml'))
      ff = File.read(file)
      yaml_dict = YAML.safe_load(ff)

      unless yaml_dict.key?('rules')
        puts "#{file}: no rules key"
        exit(1)
      end

      if yaml_dict['rules'].length != 1
        puts "#{file}: one rule per file policy violated"
        exit(1)
      end

      # extract comments
      comments = ff.scan(/^#.*?$/)
      comments.filter! { |r| !r.include?('yamllint') }

      next if yaml_dict == false

      astyaml = AutoFormat.reformat_yaml(id, yaml_dict)

      tmpfile = "#{file}_tmp"
      File.open(tmpfile, 'w') do |f|
        if comments.any?
          f.puts('# yamllint disable')
          f.puts(comments) if comments.any?
          f.puts('# yamllint enable')
        end
        f.puts(astyaml)
      end

      s = StringIO.new
      File.readlines(tmpfile).each do |line|
        if line.start_with?("    source-rule-url:")
          s << "    # yamllint disable\n"
          s << line
          s << "    # yamllint enable\n"
        else
          s << line
        end
      end

      File.write(tmpfile, s.string)

      if FileUtils.identical?(file, "#{file}_tmp")
        puts("#{file}: ✔")
        FileUtils.rm("#{file}_tmp")
      else
        changed += 1
        puts("#{file}: ✘")
        FileUtils.mv("#{file}_tmp", file)
      end
    end
    changed
  end

  def self.make_quoted(value)
    value.style = Psych::Nodes::Scalar::DOUBLE_QUOTED
    value.quoted = true
  end
end

