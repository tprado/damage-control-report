package spock.damagecontrol

class StepDefinitionParserTest extends BaseSpec {

    StepDefinitionParser parser

    def setup() {
        given:
        parser = new StepDefinitionParser()
    }

    def 'should handle a step with no description'() {
        given:
        def featureSourceCode = '''
        given:
        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'given'
        and:
        steps[0].description == ''
    }

    def 'should have a "given" statement in blocks'() {
        given:
        def featureSourceCode = '''
        given: 'something'
        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'given'
        and:
        steps[0].description == "'something'"
    }

    def 'should have a "when" statement in blocks'() {
        given:
        def featureSourceCode = '''
        when: 'I do something'
        //other steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'when'
        and:
        steps[0].description == "'I do something'"
    }

    def 'should have a "then" statement in blocks'() {
        given:
        def featureSourceCode = '''
        then: 'something happens'
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'then'
        and:
        steps[0].description == "'something happens'"
    }

    def 'should have an "and" statement in blocks'() {
        given:
        def featureSourceCode = '''
        and: 'something else happens'
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'and'
        and:
        steps[0].description == "'something else happens'"
    }

    def 'should have an "expect" statement in blocks'() {
        given:
        def featureSourceCode = '''
        expect: ''
        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'expect'
        and:
        steps[0].description == "''"
    }

    def 'should have a "setup" statement in blocks'() {
        given:
        def featureSourceCode = '''
        setup: 'something'
        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'setup'
        and:
        steps[0].description == "'something'"
    }

    def 'should have a "cleanup" statement in blocks'() {
        given:
        def featureSourceCode = '''
        cleanup: 'something'
        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'cleanup'
        and:
        steps[0].description == "'something'"
    }

    def 'should have all the blocks in the correct order'() {
        given:
        def featureSourceCode = '''
        given: 'something'
        // some steps
        when: 'I do something'
        //other steps
        and: 'I do something else'
        //other steps
        then: 'something happens'
        and: 'something else happens'
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'given'
        steps[1].type == 'when'
        steps[2].type == 'and'
        steps[3].type == 'then'
        steps[4].type == 'and'
    }
}
