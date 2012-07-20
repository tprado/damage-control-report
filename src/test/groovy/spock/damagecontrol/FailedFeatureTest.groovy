package spock.damagecontrol

import static spock.damagecontrol.Results.FAILED
import static spock.damagecontrol.Results.PASSED
import static spock.damagecontrol.Results.NOT_PERFORMED
import static spock.damagecontrol.Results.UNKNOWN

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
        feature.result == FAILED
    }

    def 'should identify which step failed'() {
        given:
        feature.startLineNumber = 5
        feature.endLineNumber = 25
        feature.steps[0] = new Step(lineNumber: 10)
        feature.steps[1] = new Step(lineNumber: 20)
        feature.details = 'at spock.damagecontrol.SomeSpecTest.feature name(SomeSpecTest.groovy:21)'

        when:
        feature.identifyStepsResult('spock.damagecontrol.SomeSpecTest')

        then:
        feature.steps[1].result == FAILED
    }

    def 'should identify which step passed'() {
        given:
        feature.startLineNumber = 5
        feature.endLineNumber = 25
        feature.steps[0] = new Step(lineNumber: 10)
        feature.steps[1] = new Step(lineNumber: 20)
        feature.details = 'at spock.damagecontrol.SomeSpecTest.feature name(SomeSpecTest.groovy:21)'

        when:
        feature.identifyStepsResult('spock.damagecontrol.SomeSpecTest')

        then:
        feature.steps[0].result == PASSED
    }

    def 'should identify which step was not performed'() {
        given:
        feature.startLineNumber = 5
        feature.endLineNumber = 25
        feature.steps[0] = new Step(lineNumber: 10)
        feature.steps[1] = new Step(lineNumber: 20)
        feature.details = 'at spock.damagecontrol.SomeSpecTest.feature name(SomeSpecTest.groovy:15)'

        when:
        feature.identifyStepsResult('spock.damagecontrol.SomeSpecTest')

        then:
        feature.steps[1].result == NOT_PERFORMED
    }

    def 'should ignore which step failed if error was not in the feature'() {
        given:
        feature.startLineNumber = 5
        feature.endLineNumber = 25
        feature.steps[0] = new Step(lineNumber: 10)
        feature.steps[1] = new Step(lineNumber: 20)
        feature.details = 'at spock.damagecontrol.SomeSpecTest.feature name(SomeSpecTest.groovy:30)'

        when:
        feature.identifyStepsResult('spock.damagecontrol.SomeSpecTest')

        then:
        feature.steps[0].result == UNKNOWN
        and:
        feature.steps[1].result == UNKNOWN
    }
}
