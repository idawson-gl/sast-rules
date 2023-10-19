#!/usr/bin/env ruby

module FileCheck
  def self.run
    ok = true
    Dir.entries('./').select { |f| File.directory?(f) }.each do |lang|
      next if ['.git', '..', '.', 'ci'].include?(lang)

      Dir.glob("#{lang}/**/rule-*.yml").each do |file|
        dirname = File.dirname(file)

        if Dir.glob("#{dirname}/test-*.*").count.zero?
          puts("no test cases in #{dirname}: ✘")
          ok = false
        end
      end
    end
    ok
  end
end

if FileCheck.run
  exit(0)
else
  exit(-1)
end
