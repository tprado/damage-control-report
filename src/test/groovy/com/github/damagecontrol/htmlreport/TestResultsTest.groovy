package com.github.damagecontrol.htmlreport

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
}
