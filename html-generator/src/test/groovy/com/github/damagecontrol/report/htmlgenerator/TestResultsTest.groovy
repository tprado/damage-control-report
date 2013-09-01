package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.FAILED
import static com.github.damagecontrol.report.htmlgenerator.Results.PASSED
import static com.github.damagecontrol.report.htmlgenerator.Results.SKIPPED

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

    def 'should count number of specs'() {
        given:
        results.spec('spec 1').passed('feature 1.1')
        results.spec('spec 2').passed('feature 2.1')

        expect:
        results.specCount == 2
    }

    def 'should count number of failed specs'() {
        given:
        results.spec('spec 1').passed('feature 1.1')
        results.spec('spec 2').failed('feature 2.1')

        expect:
        results.failedSpecCount == 1
    }

    def 'should count number of skipped specs'() {
        given:
        results.spec('spec 1').passed('feature 1.1')
        results.spec('spec 2').skipped('feature 2.1')

        expect:
        results.skippedSpecCount == 1
    }

    def 'should result in "failed" if there is at least 1 failed spec'() {
        given:
        results.spec('spec 1').passed('feature 1.1')
        results.spec('spec 2').failed('feature 2.1')

        expect:
        results.result == FAILED
    }

    def 'should result in "skipped" if all specs are skipped'() {
        given:
        results.spec('spec 1').skipped('feature 1.1')
        results.spec('spec 2').skipped('feature 2.1')

        expect:
        results.result == SKIPPED
    }

    def 'should result in "passed" if there is no failures and at least 1 success feature'() {
        given:
        results.spec('spec 1').passed('feature 1.1')
        results.spec('spec 2').skipped('feature 2.1')

        expect:
        results.result == PASSED
    }

    def 'should indicate 100% successful specs for no failures'() {
        given:
        results.spec('spec 1').passed('feature 1.1')
        results.spec('spec 2').passed('feature 2.1')

        expect:
        results.successPercentage == 100
    }

    def 'should indicate percentage of successful features'() {
        given:
        results.spec('spec 1').passed('feature 1.1')
        results.spec('spec 2').failed('feature 2.1')
        results.spec('spec 3').failed('feature 3.1')

        expect:
        results.successPercentage == 33
    }

    def 'should indicate 0% successful features for all features failed'() {
        given:
        results.spec('spec 1').failed('feature 1.1')
        results.spec('spec 2').failed('feature 2.1')

        expect:
        results.successPercentage == 0
    }
}
