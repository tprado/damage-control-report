package spock.damagecontrol

class FeatureTest extends BaseSpec {

    def 'should fail the feature'() {
        given:
        Feature feature = new Feature()

        when:
        feature.fail 'error', 'error details'

        then:
        feature.failed
    }

    def 'should ignore the feature'() {
        given:
        Feature feature = new Feature()

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
        feature.ignore()

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
