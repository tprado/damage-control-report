package spock.damagecontrol

class SpecTest extends BaseSpec {

    Spec spec
    def feature

    def setup() {
        spec = new Spec(name: 'samples.SampleSpecificationTest')
        feature = new Feature(name:  'some feature')
        spec.features['some feature'] = feature
    }

    def 'should create new feature if not present'() {
        when:
        def feature = spec.feature('feature name')

        then:
        feature.name == 'feature name'
    }

    def 'should return feature if present'() {
        given:
        def feature1 = spec.feature('feature name')

        when:
        def feature2 = spec.feature('feature name')

        then:
        feature1 == feature2
    }

    def 'should have output by default'() {
        expect:
        new Spec().output
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

    def 'should parse definition for all features'() {
        given:
        String sourceCode = '''
class Spec1 {
    def 'some feature'() {
        expect: 'some block'
        // some blocks
    }
}
'''
        when:
        spec.parseEachFeatureDefinition(sourceCode)

        then:
        feature.steps[0].type == 'expect'
    }
}
