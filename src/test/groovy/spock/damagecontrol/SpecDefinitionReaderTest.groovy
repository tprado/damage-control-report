package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyFileToDirectory

class SpecDefinitionReaderTest extends BaseFileHandlingSpec {

    private static final String SAMPLE_FOLDER = 'src/test/resources'
    private static final File SPEC_DEFINITION = new File(SAMPLE_FOLDER + '/samples/definitions/SampleSpecDefinitionTest.groovy')

    private File specsDefinitionPackage

    def setup() {
        specsDefinitionPackage = new File(testFolder.absolutePath + '/samples/definitions')
    }

    def 'should read spec definition inside a package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackage)
        Spec spec = new Spec('samples.definitions.SampleSpecDefinitionTest')

        when:
        SpecDefinition specDefinition = new SpecDefinitionReader(testFolder).read(spec)

        then:
        specDefinition.sourceCode.contains('class SampleSpecDefinitionTest')
    }

    def 'should read spec definition inside the default package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, testFolder)
        Spec spec = new Spec('SampleSpecDefinitionTest')

        when:
        SpecDefinition specDefinition = new SpecDefinitionReader(testFolder).read(spec)

        then:
        specDefinition.sourceCode.contains('class SampleSpecDefinitionTest')
    }
}
