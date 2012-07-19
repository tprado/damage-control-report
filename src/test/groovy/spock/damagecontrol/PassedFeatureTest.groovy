package spock.damagecontrol

class PassedFeatureTest extends BaseFeatureSpec {

    def setup() {
        given:
        feature = new PassedFeature(name: featureName)
    }

    def 'should indicate not failed'() {
        expect:
        !feature.failed
    }

    def 'should indicate not ignored'() {
        expect:
        !feature.ignored
    }

    def 'should indicate the result as "passed"'() {
        expect:
        feature.result == 'passed'
    }
}
