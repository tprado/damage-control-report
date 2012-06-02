package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyFileToDirectory

class ReportTest extends BaseFileHandlingSpec {

    def static final SAMPLE_FOLDER = 'src/test/resources/samples'
    def static final SPEC_DEFINITION = new File(SAMPLE_FOLDER + '/definitions/SampleSpecDefinitionTest.groovy')
    def static final SPEC_RESULT = new File(SAMPLE_FOLDER + '/results/TEST-samples.definitions.SampleSpecDefinitionTest.xml')

    def specsDefinitionPackage
    def report
    def outputFolder

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

    def 'should create index file'() {
        when:
        report.run()

        then:
        new File(outputFolder.absolutePath + '/index.html').exists()
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
