package spock.damagecontrol

import spock.lang.Specification

import static org.apache.commons.io.FileUtils.copyFileToDirectory
import static org.apache.commons.io.FileUtils.deleteDirectory

class SpecDefinitionReaderTest extends Specification {

    private static final String SAMPLE_FOLDER = 'src/test/resources/samples'

    private static final File SPEC_DEFINITION = new File(SAMPLE_FOLDER + '/SampleSpecDefinitionTest.groovy')

    private static final File SPECS_DEFINITION_FOLDER = new File('build/' + SpecDefinitionReaderTest.class.name)
    private static final File SPECS_DEFINITION_PACKAGE = new File('build/' + SpecDefinitionReaderTest.class.name + '/samples')

    def setup() {
        deleteDirectory(SPECS_DEFINITION_FOLDER);
        SPECS_DEFINITION_FOLDER.mkdirs()
    }

    def cleanup() {
        deleteDirectory(SPECS_DEFINITION_FOLDER);
    }

    def 'should read spec definition inside a package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, SPECS_DEFINITION_PACKAGE)
        Spec spec = new Spec('samples.SampleSpecDefinitionTest')

        when:
        SpecDefinition specDefinition = new SpecDefinitionReader(SPECS_DEFINITION_FOLDER).read(spec)

        then:
        specDefinition.sourceCode.contains('class SampleSpecDefinitionTest')
    }

    def 'should read spec definition inside the default package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, SPECS_DEFINITION_FOLDER)
        Spec spec = new Spec('SampleSpecDefinitionTest')

        when:
        SpecDefinition specDefinition = new SpecDefinitionReader(SPECS_DEFINITION_FOLDER).read(spec)

        then:
        specDefinition.sourceCode.contains('class SampleSpecDefinitionTest')
    }
}
