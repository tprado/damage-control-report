package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyFileToDirectory
import static org.apache.commons.io.FilenameUtils.separatorsToSystem

class SpecReadDefinitionTest extends BaseFileHandlingSpec {

    static final SAMPLE_FOLDER = 'src/test/resources'
    static final SPEC_DEFINITION = new File(SAMPLE_FOLDER + '/samples/definitions/SampleSpecDefinitionTest.groovy')

    def specsDefinitionPackage

    def setup() {
        specsDefinitionPackage = new File(testFolder.absolutePath + '/samples/definitions')
    }

    def 'should compose spec file name based on the spec name'() {
        given:
        File baseFolder = new File('src/test/resources')
        Spec spec = new Spec(name: 'samples.SampleSpecificationTest')

        when:
        File specFile = spec.groovyFile(baseFolder)

        then:
        specFile.absolutePath.contains(separatorsToSystem('src/test/resources/samples/SampleSpecificationTest.groovy'))
    }

    def 'should read spec and feature definition inside the default package'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, testFolder)
        Spec spec = new Spec(name: 'SampleSpecDefinitionTest')
        spec.features['should do something'] = new Feature(name: 'should do something')

        when:
        spec.readDefinitionFrom testFolder

        then:
        spec.features['should do something'].steps[0].type == 'given'
        and:
        spec.features['should do something'].steps[0].description == "'I did something'"
    }

}
