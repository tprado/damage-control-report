package spock.damagecontrol

class FeatureTest extends BaseSpec {

    def feature

    def setup() {
        given:
        feature = new Feature(name: 'feature name')
    }

    def 'should have a name'() {
        expect:
        feature.name == 'feature name'
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

    def 'should have empty step list by default'() {
        expect:
        new Feature().steps == []
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
