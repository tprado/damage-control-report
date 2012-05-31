package spock.damagecontrol

class FeatureTest extends BaseSpec {

    def 'should indicate "passed" when there is no failure'() {
        given:
        Feature feature = new Feature()

        expect:
        feature.result == 'passed'
    }

    def 'should indicate "failed" when there is a failure'() {
        given:
        Feature feature = new Feature()
        feature.failed 'error', 'some error detailed'

        expect:
        feature.result == 'failed'
    }
}
