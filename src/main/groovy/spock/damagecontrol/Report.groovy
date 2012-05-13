package spock.damagecontrol

import static org.apache.commons.io.FileUtils.writeStringToFile

class Report {

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

            HtmlSpecDefinitionFormatter formatter = new HtmlSpecDefinitionFormatter(spec, specDefinition)

            writeStringToFile(formatter.file(outputFolder), formatter.format());
        })
    }
}
