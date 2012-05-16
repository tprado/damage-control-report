package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyURLToFile
import static org.apache.commons.io.FileUtils.writeStringToFile

class Report {

    private static final URL CSS_URL = Report.class.getResource('/spock/damagecontrol/statics/style/damage-control.css')

    private final TestResultsCollector resultsCollector
    private final SpecDefinitionReader definitionReader
    private final File outputFolder

    Report(config) {
        resultsCollector = new TestResultsCollector(config.testResultsFolder)
        definitionReader = new SpecDefinitionReader(config.specDefinitionsFolder)
        outputFolder = config.outputFolder
    }

    def run() {
        resultsCollector.forEach({ spec ->
            SpecDefinition specDefinition = definitionReader.read(spec)

            HtmlSpecFormatter formatter = new HtmlSpecFormatter(spec, specDefinition)

            writeStringToFile(formatter.file(outputFolder), formatter.format());
        })

        copyURLToFile(CSS_URL, new File(outputFolder.absolutePath + '/style/damage-control.css'))
    }
}
