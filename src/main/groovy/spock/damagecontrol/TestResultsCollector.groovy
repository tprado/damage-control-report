package spock.damagecontrol

class TestResultsCollector {

    final results = new TestResults()

    def collect(reader) {
        Node testSuite = new XmlParser().parse(reader)

        collectTestSuite(testSuite)
        collectTestCases(testSuite)
        collectIgnoredTestCases(testSuite)
    }

    private collectTestSuite(testSuite) {
        Spec spec = results.spec(testSuite.'@name')

        spec.duration = testSuite.'@time'
        spec.output.standard = testSuite.'system-out' ? testSuite.'system-out'[0].text() : ''
        spec.output.error = testSuite.'system-err' ? testSuite.'system-err'[0].text() : ''
    }

    private collectTestCases(testSuite) {
        testSuite.'testcase'.each { testCase ->
            def feature

            if (testCase.failure) {
                feature = results.spec(testCase.'@classname').failed(testCase.'@name')
                feature.failure.message = testCase.failure[0].'@message'
                feature.failure.details = testCase.failure[0].text()
            } else if (testCase.skipped) {
                feature = results.spec(testCase.'@classname').skipped(testCase.'@name')
            } else {
                feature = results.spec(testCase.'@classname').passed(testCase.'@name')
            }

            feature.duration = testCase.'@time'
        }
    }

    private collectIgnoredTestCases(testSuite) {
        testSuite.'ignored-testcase'.each { testCase ->
            results.spec(testCase.'@classname').skipped(testCase.'@name')
        }
    }
}
