package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyFileToDirectory

class ReportTest extends BaseFileHandlingSpec {

    private static final String SAMPLE_FOLDER = 'src/test/resources/samples'
    private static final File SPEC_DEFINITION = new File(SAMPLE_FOLDER + '/definitions/SampleSpecDefinitionTest.groovy')
    private static final File SPEC_RESULT = new File(SAMPLE_FOLDER + '/results/TEST-samples.definitions.SampleSpecDefinitionTest.xml')

    private File specsDefinitionPackage

    private Report report
    private File outputFolder

    def setup() {
        specsDefinitionPackage = new File(testFolder.absolutePath + '/samples/definitions')

        outputFolder = new File(testFolder.absolutePath + '/report')

        report = new Report([
                testResultsFolder: testFolder,
                specDefinitionsFolder: testFolder,
                outputFolder: outputFolder
        ])
    }

    def 'should copy static resources'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackage)
        copyFileToDirectory(SPEC_RESULT, testFolder)

        when:
        report.run()

        then:
        new File(outputFolder.absolutePath + '/style/damage-control.css').exists()
    }

    def 'should create html file for each spec'() {
        given:
        copyFileToDirectory(SPEC_DEFINITION, specsDefinitionPackage)
        copyFileToDirectory(SPEC_RESULT, testFolder)

        when:
        report.run()

        then:
        new File(outputFolder.absolutePath + '/samples.definitions.SampleSpecDefinitionTest.html').exists()
    }
}
