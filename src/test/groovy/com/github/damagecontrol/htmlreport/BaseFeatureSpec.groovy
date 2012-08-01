package com.github.damagecontrol.htmlreport

import spock.lang.Shared

abstract class BaseFeatureSpec extends BaseSpec {

    final featureName = 'feature name'

    @Shared
    def feature

    def lineAnnotatedSourceCode = '''#1#
#2#class Spec1 {
#3#    def 'feature name'() {
#4#        expect: 'some block'
#5#        // some blocks
#6#    }
#7#}
'''
    def lineAnnotatedSourceCodeThatDoesNotMatch = '''#1#
#2#class Spec1 {
#3#    def 'feature name that does not match'() {
#4#        expect: 'some block'
#5#        // some blocks
#6#    }
#7#}
'''

    def 'should have a name'() {
        expect:
        feature.name == featureName
    }

    def 'should have empty step list by default'() {
        expect:
        feature.steps == []
    }

    def 'should parse feature definition'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCode)

        then:
        feature.steps[0].type == 'expect'
    }

    def 'should have start line number'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCode)

        then:
        feature.startLineNumber == 4
    }

    def 'should have end line number'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCode)

        then:
        feature.endLineNumber == 6
    }

    def 'should not have start line number if feature definition can not be found'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCodeThatDoesNotMatch)

        then:
        feature.startLineNumber == 0
    }

    def 'should not have end line number if feature definition can not be found'() {
        when:
        feature.parseDefinition(lineAnnotatedSourceCodeThatDoesNotMatch)

        then:
        feature.endLineNumber == 0
    }
}
