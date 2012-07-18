package spock.damagecontrol

class TestResultsCollector {

    final results = new TestResults()

    def collect(reader) {
        Node testSuite = new XmlParser().parse(reader)

        def sysout = testSuite.'system-out' ? testSuite.'system-out'[0].text() : ''
        def syserr = testSuite.'system-err' ? testSuite.'system-err'[0].text() : ''
        
        SpecOutput output = new SpecOutput(standard: sysout, error: syserr)

        testSuite.'testcase'.each { testCase ->
            Feature feature = results.addFeature testCase.'@classname', testCase.'@name', output
            feature.duration = testCase.'@time'

            if (testCase.skipped) {
                feature.ignore()
            }

            if (testCase.failure) {
                feature.fail testCase.failure[0].'@message', testCase.failure[0].text()
            }
        }

        testSuite.'ignored-testcase'.each { testCase ->
            Feature feature = results.addFeature testCase.'@classname', testCase.'@name', output
            feature.ignore()
        }

        if (results.specs[testSuite.'@name']) {
            results.specs[testSuite.'@name'].duration = testSuite.'@time'
        }
    }
}
