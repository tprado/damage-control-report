package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyURLToFile

class Report {

    static final CSS_URL = Report.getResource('/spock/damagecontrol/statics/style/damage-control.css')

    final indexTemplate = new HtmlIndexTemplate()
    final specTemplate = new HtmlSpecTemplate()

    def testResultsFolder
    def specDefinitionsFolder
    def outputFolder

    def run() {
        List specs = new TestResultsCollector(resultsFolder: testResultsFolder).collect().specList

        HtmlFileWriter writer = new HtmlFileWriter(outputFolder: outputFolder)
        GroovyFileReader reader = new GroovyFileReader(inputFolder: specDefinitionsFolder)

        writer.write('index', indexTemplate.generate(specs))

        specs.each { spec ->
            spec.parseEachFeatureDefinition(reader.read(spec.name))
            writer.write(spec.name, specTemplate.generate(spec))
        }

        copyURLToFile(CSS_URL, new File(outputFolder.absolutePath + '/style/damage-control.css'))
    }
}
