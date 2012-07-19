package spock.damagecontrol

class FailedFeatureTest extends BaseFeatureSpec {

    def setup() {
        given:
        feature = new FailedFeature(name: featureName)
    }

    def 'should indicate failed'() {
        expect:
        feature.failed
    }

    def 'should indicate not ignored'() {
        expect:
        !feature.ignored
    }

    def 'should indicate the result as "failed"'() {
        expect:
        feature.result == 'failed'
    }

    def 'should have failure details'() {
        expect:
        feature.failure
    }
}
