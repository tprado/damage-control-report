package spock.damagecontrol

class FeatureTest extends BaseSpec {

    def 'should indicate the result as "passed" when there is no failure'() {
        given:
        Feature feature = new Feature()

        expect:
        feature.result == 'passed'
    }

    def 'should indicate the result as "failed" when there is a failure'() {
        given:
        Feature feature = new Feature()
        feature.fail 'error', 'some error detailed'

        expect:
        feature.result == 'failed'
    }

    def 'should indicate the result as "skipped" when the test is ignored'() {
        given:
        Feature feature = new Feature()
        feature.ignored = true

        expect:
        feature.result == 'skipped'
    }

    def 'should indicate if the feature failed'() {
        given:
        Feature feature = new Feature()
        feature.fail 'error', 'some error detailed'

        expect:
        feature.failed
    }
}
