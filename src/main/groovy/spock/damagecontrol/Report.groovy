package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyURLToFile
import static org.apache.commons.io.FileUtils.writeStringToFile

class Report {

    static final CSS_URL = Report.getResource('/spock/damagecontrol/statics/style/damage-control.css')

    def testResultsFolder
    def specDefinitionsFolder
    def outputFolder

    def run() {
        List specs = new TestResultsCollector(resultsFolder: testResultsFolder).collect().specList

        HtmlIndexTemplate indexTemplate = new HtmlIndexTemplate(specs: specs)
        writeStringToFile(new File(outputFolder.absolutePath + '/index.html'), indexTemplate.generate())

        specs.each { spec ->
            spec.readDefinitionFrom specDefinitionsFolder
            HtmlSpecTemplate specTemplate = new HtmlSpecTemplate(spec: spec)
            writeStringToFile(specTemplate.file(outputFolder), specTemplate.generate());
        }

        copyURLToFile(CSS_URL, new File(outputFolder.absolutePath + '/style/damage-control.css'))
    }
}
