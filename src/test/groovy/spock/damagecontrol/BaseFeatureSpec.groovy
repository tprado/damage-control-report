package spock.damagecontrol

import spock.lang.Shared

abstract class BaseFeatureSpec extends BaseSpec {

    final featureName = 'feature name'

    @Shared
    def feature

    def 'should have a name'() {
        expect:
        feature.name == featureName
    }

    def 'should have empty step list by default'() {
        expect:
        feature.steps == []
    }

    def 'should parse feature definition'() {
        given:
        String lineAnnotatedSourceCode = '''#1#
#2#class Spec1 {
#3#    def 'feature name'() {
#4#        expect: 'some block'
#5#        // some blocks
#6#    }
#7#}
'''
        when:
        feature.parseDefinition(lineAnnotatedSourceCode)

        then:
        feature.steps[0].type == 'expect'
    }
}
