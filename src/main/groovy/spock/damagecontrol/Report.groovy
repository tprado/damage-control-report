package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyURLToFile
import static org.apache.commons.io.FileUtils.writeStringToFile

class Report {

    static final CSS_URL = Report.getResource('/spock/damagecontrol/statics/style/damage-control.css')

    final resultsCollector
    final outputFolder

    Report(config) {
        resultsCollector = new TestResultsCollector(config.testResultsFolder)
        outputFolder = config.outputFolder
    }

    def run() {
        List specs = resultsCollector.collect().specList

        HtmlIndexTemplate indexTemplate = new HtmlIndexTemplate(specs)
        writeStringToFile(new File(outputFolder.absolutePath + '/index.html'), indexTemplate.generate())

        specs.each { spec ->
            HtmlSpecTemplate specTemplate = new HtmlSpecTemplate(spec)
            writeStringToFile(specTemplate.file(outputFolder), specTemplate.generate());
        }

        copyURLToFile(CSS_URL, new File(outputFolder.absolutePath + '/style/damage-control.css'))
    }
}
