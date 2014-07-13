package com.github.damagecontrol.report.htmlgenerator

class TestResultsTest extends BaseSpec {

    TestResults results

    def setup() {
        given:
        results = new TestResults()
    }

    def 'should create new spec if not present'() {
        when:
        def spec = results.spec('spec name')

        then:
        spec.name == 'spec name'
    }

    def 'should return same spec if present'() {
        given:
        def spec1 = results.spec('spec name')

        when:
        def spec2 = results.spec('spec name')

        then:
        spec1 == spec2
    }

    def 'should summarize number of features'() {
        given:
        results.spec('spec 1').passed('feature 1.1')
        results.spec('spec 1').passed('feature 1.2')
        results.spec('spec 2').passed('feature 2.1')

        expect:
        results.summary.count == 3
    }

    def 'should sort specs by name'() {
        given:
        def specNames = []
        results.spec('spec B').passed('feature 1.1')
        results.spec('spec A').passed('feature 1.2')
        results.spec('spec C').passed('feature 2.1')

        when:
        results.specs.each { name, spec -> specNames.add(name) }

        then:
        specNames == ['spec A', 'spec B', 'spec C']
    }
}
