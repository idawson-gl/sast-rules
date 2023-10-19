#!/usr/bin/env ruby

require_relative './autoformat'

changed = AutoFormat.run

puts "#{changed} formatted files"

exit(-1) if changed > 0

exit(0)
