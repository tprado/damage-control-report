package spock.damagecontrol

class TestResultsCollector {

    final results = new TestResults()

    def collect(reader) {
        Node testSuite = new XmlParser().parse(reader)

        Spec spec = results.spec(testSuite.'@name')

        spec.duration = testSuite.'@time'
        spec.output.standard = testSuite.'system-out' ? testSuite.'system-out'[0].text() : ''
        spec.output.error = testSuite.'system-err' ? testSuite.'system-err'[0].text() : ''
        
        testSuite.'testcase'.each { testCase ->
            Feature feature = results.spec(testCase.'@classname').feature(testCase.'@name')
            feature.duration = testCase.'@time'

            if (testCase.failure) {
                feature.fail(testCase.failure[0].'@message', testCase.failure[0].text())
            } else if (testCase.skipped) {
                feature.ignore()
            }
        }

        testSuite.'ignored-testcase'.each { testCase ->
            results.spec(testCase.'@classname').feature(testCase.'@name').ignore()
        }
    }
}
