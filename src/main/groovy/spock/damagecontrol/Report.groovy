package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyURLToFile
import static org.apache.commons.io.FileUtils.writeStringToFile

class Report {

    def static final CSS_URL = Report.class.getResource('/spock/damagecontrol/statics/style/damage-control.css')

    def final resultsCollector
    def final definitionReader
    def final outputFolder

    Report(config) {
        resultsCollector = new TestResultsCollector(config.testResultsFolder)
        definitionReader = new SpecDefinitionReader(config.specDefinitionsFolder)
        outputFolder = config.outputFolder
    }

    def run() {
        Map specs = resultsCollector.collectSpecs()

        HtmlIndexTemplate indexTemplate = new HtmlIndexTemplate(new ArrayList(specs.values()))
        writeStringToFile(new File(outputFolder.absolutePath + '/index.html'), indexTemplate.generate())

        specs.each {specName, spec ->
            definitionReader.read(spec)
            HtmlSpecTemplate specTemplate = new HtmlSpecTemplate(spec)
            writeStringToFile(specTemplate.file(outputFolder), specTemplate.generate());
        }

        copyURLToFile(CSS_URL, new File(outputFolder.absolutePath + '/style/damage-control.css'))
    }
}
