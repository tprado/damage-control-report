package spock.damagecontrol

import static org.apache.commons.io.FileUtils.iterateFiles

class TestResultsCollector {

    private static final boolean INCLUDE_SUB_FOLDERS = true
    private static final String[] XML = ['xml']

    private final File resultsFolder

    TestResultsCollector(resultsFolder) {
        this.resultsFolder = resultsFolder
    }

    def collectSpecs() {
        TestResults results = collect()

        Map specs = [:]
        results.specs.values().each({ spec ->
            specs[spec.name] = spec
        })
        return specs
    }

    private TestResults collect() {
        TestResults results = new TestResults()

        iterateFiles(resultsFolder, XML, INCLUDE_SUB_FOLDERS).each {file ->
            try {
                collectSpecs(file, results)
            } catch (Exception e) {
                println "Error reading file '${file}': ${e.message}"
            }
        }

        return results
    }

    private void collectSpecs(File file, TestResults results) {
        Node testSuite = parse(file)

        def sysout = testSuite.'system-out' ? testSuite.'system-out'[0].text() : ''
        def syserr = testSuite.'system-err' ? testSuite.'system-err'[0].text() : ''
        
        SpecOutput output = new SpecOutput(sysout, syserr)

        testSuite.'testcase'.each { testCase ->
            Feature feature = results.addFeature testCase.'@classname', testCase.'@name', output

            if (testCase.skipped) {
                feature.ignored = true
            }

            if (testCase.failure) {
                feature.failed testCase.failure[0].'@message', testCase.failure[0].text()
            }
        }

        testSuite.'ignored-testcase'.each { testCase ->
            Feature feature = results.addFeature testCase.'@classname', testCase.'@name', output
            feature.ignored = true
        }
    }

    private Node parse(File file) {
        new XmlParser().parse(file)
    }

    def getSpecList() {

    }
}
