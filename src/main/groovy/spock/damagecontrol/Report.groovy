package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyURLToFile

class Report {

    private static final CSS_URL = Report.getResource('/spock/damagecontrol/statics/style/damage-control.css')

    def testResultsFolder
    def specDefinitionsFolder
    def outputFolder

    private final collector = new TestResultsCollector()
    private final indexTemplate = new HtmlIndexTemplate()
    private final specTemplate = new HtmlSpecTemplate()

    def run() {
        new XmlFileReader(inputFolder: testResultsFolder).forEach { collector.collect(it) }
        List specs = collector.results.specList

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
