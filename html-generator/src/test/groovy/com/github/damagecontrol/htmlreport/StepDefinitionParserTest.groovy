package com.github.damagecontrol.htmlreport

class StepDefinitionParserTest extends BaseSpec {

    StepDefinitionParser parser

    def setup() {
        given:
        parser = new StepDefinitionParser()
    }

    def 'should handle a step with no description'() {
        given:
        def featureSourceCode = '''#10#
#11#        given:
#12#        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'given'
        and:
        steps[0].description == ''
        and:
        steps[0].lineNumber == 11
    }

    def 'should have a "given" statement in steps'() {
        given:
        def featureSourceCode = '''#10#
#11#        given: 'something'
#12#        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'given'
        and:
        steps[0].description == "'something'"
        and:
        steps[0].lineNumber == 11
    }

    def 'should have a "when" statement in steps'() {
        given:
        def featureSourceCode = '''#10#
#11#        when: 'I do something'
#12#        //other steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'when'
        and:
        steps[0].description == "'I do something'"
        and:
        steps[0].lineNumber == 11
    }

    def 'should have a "then" statement in steps'() {
        given:
        def featureSourceCode = '''#10#
#11#        then: 'something happens'
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'then'
        and:
        steps[0].description == "'something happens'"
        and:
        steps[0].lineNumber == 11
    }

    def 'should have an "and" statement in steps'() {
        given:
        def featureSourceCode = '''#10#
#11#        and: 'something else happens'
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'and'
        and:
        steps[0].description == "'something else happens'"
        and:
        steps[0].lineNumber == 11
    }

    def 'should have an "expect" statement in steps'() {
        given:
        def featureSourceCode = '''#10#
#11#        expect: ''
#12#        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'expect'
        and:
        steps[0].description == "''"
        and:
        steps[0].lineNumber == 11
    }

    def 'should have a "setup" statement in steps'() {
        given:
        def featureSourceCode = '''#10#
#11#        setup: 'something'
#12#        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'setup'
        and:
        steps[0].description == "'something'"
        and:
        steps[0].lineNumber == 11
    }

    def 'should have a "cleanup" statement in steps'() {
        given:
        def featureSourceCode = '''#10#
#11#        cleanup: 'something'
#12#        // some steps
'''

        when:
        def steps = parser.parse(featureSourceCode)

        then:
        steps[0].type == 'cleanup'
        and:
        steps[0].description == "'something'"
        and:
        steps[0].lineNumber == 11
    }

    def 'should have all the steps in the correct order'() {
        given:
        def featureSourceCode = '''#10#
#11#        given: 'something'
#12#        // some steps
#13#        when: 'I do something'
#14#        //other steps
#15#        and: 'I do something else'
#16#        //other steps
#17#        then: 'something happens'
#18#        and: 'something else happens'
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
