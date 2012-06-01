package spock.damagecontrol

import static org.apache.commons.io.FilenameUtils.separatorsToSystem

class SpecTest extends BaseSpec {

    def spec
    def feature

    def setup() {
        spec = new Spec('samples.SampleSpecificationTest')
        feature = new Feature()
        spec.features['some feature'] = feature
    }

    def 'should compose spec file name based on the spec name'() {
        given:
        File baseFolder = new File('src/test/resources')

        when:
        File specFile = spec.file(baseFolder)

        then:
        specFile.absolutePath.contains(separatorsToSystem('src/test/resources/samples/SampleSpecificationTest.groovy'))
    }

    def 'should indicate no line number if there is no error'() {
        when:
        def lines = spec.errorLines()

        then:
        lines == []
    }

    def 'should indicate the line numbers where an error occurred'() {
        given:
        feature.fail 'error message', 'at samples.definitions.SampleSpecificationTest.shouldFail(SampleSpecificationTest.groovy:19)'

        when:
        def lines = spec.errorLines()

        then:
        lines == [19]
    }

    def 'should indicate the line numbers where an error occurred for spec in the default package'() {
        given:
        feature.fail 'error message', 'at SampleSpecificationTest.shouldFail(SampleSpecificationTest.groovy:19)'

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
}
