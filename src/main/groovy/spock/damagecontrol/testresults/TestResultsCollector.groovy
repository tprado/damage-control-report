package spock.damagecontrol.testresults

import static org.apache.commons.io.FileUtils.iterateFiles

class TestResultsCollector {

    private static final boolean INCLUDE_SUB_FOLDERS = true
    private static final String[] XML = ['xml']

    final File resultsFolder

    TestResultsCollector(File resultsFolder) {
        this.resultsFolder = resultsFolder
    }

    def forEach(Closure closure) {
        TestResults results = collect()

        results.specs.values().each({ spec ->
            closure(spec)
        })
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
        parse(file).testcase.each {testCase ->
            def feature = results.addFeature testCase.'@classname', testCase.'@name'

            if (testCase.failure) {
                feature.failed testCase.failure[0].'@message', testCase.failure[0].text()
            }
        }
    }

    private Node parse(File file) {
        new XmlParser().parse(file)
    }
}
