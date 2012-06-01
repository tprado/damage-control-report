package spock.damagecontrol

import static org.apache.commons.io.FilenameUtils.separatorsToSystem

class SpecTest extends BaseSpec {

    def 'should compose spec file name based on the spec name'() {
        given:
        File baseFolder = new File('src/test/resources')
        Spec spec = new Spec('samples.SampleSpecificationTest')

        when:
        File specFile = spec.file(baseFolder)

        then:
        specFile.absolutePath.contains(separatorsToSystem('src/test/resources/samples/SampleSpecificationTest.groovy'))
    }

    def 'should indicate no line number if there is no error'() {
        given:
        Feature feature = new Feature()
        Spec spec = new Spec('samples.definitions.SampleSpecificationTest')
        spec.features['some feature'] = feature

        when:
        def lines = spec.errorLines()

        then:
        lines == []
    }

    def 'should indicate the line numbers where an error occurred'() {
        given:
        Feature feature = new Feature()
        feature.failed('error message', 'at samples.definitions.SampleSpecificationTest.shouldFail(SampleSpecificationTest.groovy:19)')
        Spec spec = new Spec('samples.definitions.SampleSpecificationTest')
        spec.features['some feature'] = feature

        when:
        def lines = spec.errorLines()

        then:
        lines == [19]
    }

    def 'should indicate the line numbers where an error occurred for spec in the default package'() {
        given:
        Feature feature = new Feature()
        feature.failed('error message', 'at SampleSpecificationTest.shouldFail(SampleSpecificationTest.groovy:19)')
        Spec spec = new Spec('SampleSpecificationTest')
        spec.features['some feature'] = feature

        when:
        def lines = spec.errorLines()

        then:
        lines == [19]
    }

    def 'should count number of features'() {
        given:
        Spec spec = new Spec('spec name')
        spec.features['some feature'] = new Feature()

        expect:
        spec.featureCount == 1
    }
}
