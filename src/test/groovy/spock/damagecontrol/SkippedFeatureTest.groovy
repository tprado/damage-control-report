package spock.damagecontrol

import static spock.damagecontrol.Results.SKIPPED

class SkippedFeatureTest extends BaseFeatureSpec {

    def setup() {
        given:
        feature = new SkippedFeature(name: featureName)
    }

    def 'should indicate not failed'() {
        expect:
        !feature.failed
    }

    def 'should indicate ignored'() {
        expect:
        feature.ignored
    }

    def 'should indicate the result as "skipped"'() {
        expect:
        feature.result == SKIPPED
    }
}
