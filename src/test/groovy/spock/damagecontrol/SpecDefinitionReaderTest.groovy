package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyFileToDirectory

class SpecDefinitionReaderTest extends BaseFileHandlingSpec {

    static final SAMPLE_FOLDER = 'src/test/resources'
    static final SPEC_DEFINITION = new File(SAMPLE_FOLDER + '/samples/definitions/SampleSpecDefinitionTest.groovy')

    def specsDefinitionPackage

    def setup() {
        specsDefinitionPackage = new File(testFolder.absolutePath + '/samples/definitions')
    }

    def 'should read spec definition inside a package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackage)
        Spec spec = new Spec('samples.definitions.SampleSpecDefinitionTest')

        when:
        new SpecDefinitionReader(testFolder).read(spec)

        then:
        spec.sourceCode.contains('class SampleSpecDefinitionTest')
    }

    def 'should read spec and feature definition inside the default package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, testFolder)
        Spec spec = new Spec('SampleSpecDefinitionTest')
        spec.features['should do something'] = new Feature()

        when:
        new SpecDefinitionReader(testFolder).read(spec)

        then:
        spec.sourceCode.contains('class SampleSpecDefinitionTest')
        and:
        spec.features['should do something'].sourceCode.contains('I did something')
    }

}
