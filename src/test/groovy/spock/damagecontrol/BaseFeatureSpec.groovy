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
        String sourceCode = '''
class Spec1 {
    def 'feature name'() {
        expect: 'some block'
        // some blocks
    }
}
'''
        when:
        feature.parseDefinition(sourceCode)

        then:
        feature.steps[0].type == 'expect'
    }
}
