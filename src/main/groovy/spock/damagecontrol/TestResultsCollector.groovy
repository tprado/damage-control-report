package spock.damagecontrol

import static org.apache.commons.io.FileUtils.iterateFiles

class TestResultsCollector {

    private static final boolean INCLUDE_SUB_FOLDERS = true
    private static final String[] XML = ['xml']

    def collect(folder) {
        def results = new TestResults()

        iterateFiles(folder, XML, INCLUDE_SUB_FOLDERS).each {file ->
            try {
                collectSpecs(file, results)
            } catch (Exception e) {
                println "Error reading file '${file}': ${e.message}"
            }
        }

        return results
    }

    private void collectSpecs(file, results) {
        parse(file).testcase.each {testCase ->
            def feature = results.addFeature testCase.'@classname', testCase.'@name'

            if (testCase.failure) {
                feature.failed testCase.failure[0].'@message', testCase.failure[0].text()
            }
        }
    }

    private Node parse(file) {
        new XmlParser().parse(file)
    }
}
