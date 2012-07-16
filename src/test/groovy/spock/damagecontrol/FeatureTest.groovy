package spock.damagecontrol

class FeatureTest extends BaseSpec {

    def feature

    def setup(){
        given:
        feature = new Feature()
    }

    def 'should fail the feature'() {
        when:
        feature.fail 'error', 'error details'

        then:
        feature.failed
    }

    def 'should ignore the feature'() {
        when:
        feature.ignore()

        then:
        feature.ignored
    }

    def 'should not be ignored by default'() {
        expect:
        !new Feature().ignored
    }

    def 'should indicate the result as "passed" when there is no failure'() {
        expect:
        feature.result == 'passed'
    }

    def 'should indicate the result as "failed" when there is a failure'() {
        feature.fail 'error', 'some error detailed'

        expect:
        feature.result == 'failed'
    }

    def 'should indicate the result as "skipped" when the test is ignored'() {
        feature.ignore()

        expect:
        feature.result == 'skipped'
    }

    def 'should indicate if the feature failed'() {
        feature.fail 'error', 'some error detailed'

        expect:
        feature.failed
    }

    def 'should have empty source code by default'() {
        expect:
        new Feature().sourceCode == ''
    }

    def 'should handle a step with no description'() {
        when:
        feature.sourceCode = '''
        given:
        // some steps
'''

        then:
        feature.steps[0].type == 'given'
        and:
        feature.steps[0].description == ''
    }

    def 'should have a "given" statement in blocks'() {
        when:
        feature.sourceCode = '''
        given: 'something'
        // some steps
'''

        then:
        feature.steps[0].type == 'given'
        and:
        feature.steps[0].description == "'something'"
    }

    def 'should have a "when" statement in blocks'() {
        when:
        feature.sourceCode = '''
        when: 'I do something'
        //other steps
'''

        then:
        feature.steps[0].type == 'when'
        and:
        feature.steps[0].description == "'I do something'"
    }

    def 'should have a "then" statement in blocks'() {
        when:
        feature.sourceCode = '''
        then: 'something happens'
'''

        then:
        feature.steps[0].type == 'then'
        and:
        feature.steps[0].description == "'something happens'"
    }

    def 'should have an "and" statement in blocks'() {
        when:
        feature.sourceCode = '''
        and: 'something else happens'
'''

        then:
        feature.steps[0].type == 'and'
        and:
        feature.steps[0].description == "'something else happens'"
    }

    def 'should have an "expect" statement in blocks'() {
        when:
        feature.sourceCode = '''
        expect: ''
        // some steps
'''

        then:
        feature.steps[0].type =='expect'
        and:
        feature.steps[0].description == "''"
    }

    def 'should have a "setup" statement in blocks'() {
        when:
        feature.sourceCode = '''
        setup: 'something'
        // some steps
'''

        then:
        feature.steps[0].type == 'setup'
        and:
        feature.steps[0].description == "'something'"
    }

    def 'should have a "cleanup" statement in blocks'() {
        when:
        feature.sourceCode = '''
        cleanup: 'something'
        // some steps
'''

        then:
        feature.steps[0].type == 'cleanup'
        and:
        feature.steps[0].description == "'something'"
    }

    def 'should have all the blocks in the correct order'() {
        when:
        feature.sourceCode = '''
        given: 'something'
        // some steps
        when: 'I do something'
        //other steps
        and: 'I do something else'
        //other steps
        then: 'something happens'
        and: 'something else happens'
'''

        then:
        feature.steps[0].type == 'given'
        feature.steps[1].type == 'when'
        feature.steps[2].type == 'and'
        feature.steps[3].type == 'then'
        feature.steps[4].type == 'and'
    }
}
