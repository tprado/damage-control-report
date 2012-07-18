package spock.damagecontrol

class SpecTest extends BaseSpec {

    Spec spec
    def feature

    def setup() {
        spec = new Spec(name: 'samples.SampleSpecificationTest')
        feature = new Feature()
        spec.features['some feature'] = feature
    }

    def 'should indicate no line number if there is no error'() {
        when:
        def lines = spec.errorLines()

        then:
        lines == []
    }

    def 'should indicate the line numbers where an error occurred'() {
        given:
        feature.fail 'error', 'at SampleSpecificationTest.shouldFail(SampleSpecificationTest.groovy:19)'

        when:
        def lines = spec.errorLines()

        then:
        lines == [19]
    }

    def 'should count number of features'() {
        expect:
        spec.featureCount == 1
    }

    def 'should count number of failed features'() {
        given:
        feature.fail 'error', 'error detail'

        expect:
        spec.failedFeatureCount == 1
    }

    def 'should count number of skipped features'() {
        given:
        feature.ignore()

        expect:
        spec.skippedFeatureCount == 1
    }

    def 'should show "failed" for specification with at least 1 failed feature'() {
        when:
        feature.fail 'error', 'error details'

        then:
        spec.result == 'failed'
    }

    def 'should show "skipped" for specification with all skipped features'() {
        when:
        feature.ignore()

        then:
        spec.result == 'skipped'
    }

    def 'should show "passed" for specification with no failures and at least 1 success feature'() {
        expect:
        spec.result == 'passed'
    }

    def 'should indicate 100% successful features for no failures'() {
        expect:
        spec.successPercentage == 100
    }

    def 'should indicate 33% successful features'() {
        when:
        spec.features['a failed feature'] = new Feature()
        spec.features['a failed feature'].fail 'error', 'error details'
        spec.features['another failed feature'] = new Feature()
        spec.features['another failed feature'].fail 'error', 'error details'

        then:
        spec.successPercentage == 33
    }

    def 'should indicate 0% successful features for all features failed'() {
        when:
        feature.fail 'error', 'error details'

        then:
        spec.successPercentage == 0
    }

    def 'should parse feature definition'() {
        given:
        String sourceCode = '''
class Spec1 {
    def 'some feature'() {
        for (;;) {}
        // some blocks
    }
    def 'other feature'() {
        // other blocks
    }
}
'''
        when:
        spec.parseFeatureDefinition(sourceCode)

        then:
        spec.features['some feature'].sourceCode =~ /\/\/ some blocks/
    }

    def 'should ignore feature if it is not defined in the source code'() {
        given:
        String sourceCode = '''
class Spec1 {
    def 'some feature that does not match'() {
        for (;;) {}
        // some blocks
    }
}
'''

        when:
        spec.parseFeatureDefinition(sourceCode)

        then:
        spec.features['some feature'].sourceCode == ''
    }
}
