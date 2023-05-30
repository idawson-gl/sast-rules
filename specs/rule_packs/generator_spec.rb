# frozen_string_literal: true

require 'rule_packs'

RSpec.describe RulePacks::Generator do
  let(:generator) { Class.new(described_class) }
  describe 'run' do
    context 'version is empty' do
      it 'returns an error' do
        generate.run('')
      end
    end
    context 'version is not semver' do
      it 'returns an error' do
        invalid_semver = 'abc'
        generate.run(invalid_semver)
      end
    end   
  end
end