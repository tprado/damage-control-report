package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyFileToDirectory

class TestResultsCollectorTest extends BaseFileHandlingSpec {

    def static final SAMPLE_FOLDER = 'src/test/resources/samples/results'

    def static final XML_WITH_ONE_TEST_CASE = new File(SAMPLE_FOLDER + '/TEST-spock.damagecontrol.TestResultsParserTest.xml')
    def static final XML_WITH_TWO_TEST_CASES = new File(SAMPLE_FOLDER + '/TEST-spock.damagecontrol.TestResultsWith2TestCases.xml')
    def static final XML_WITH_NO_TEST_CASE = new File(SAMPLE_FOLDER + '/TEST-no-test-case.xml')
    def static final XML_WITH_IGNORED_TEST_CASE = new File(SAMPLE_FOLDER + '/TEST-spock.damagecontrol.TestResultsWithIgnoredTestCase.xml')
    def static final XML_WITH_SYS_OUT = new File(SAMPLE_FOLDER + '/TEST-spock.damagecontrol.TestResultsWithSysOut.xml')
    def static final XML_WITHOUT_SYS_OUT = new File(SAMPLE_FOLDER + '/TEST-spock.damagecontrol.TestResultsWithoutSysOut.xml')
    def static final EMPTY = new File(SAMPLE_FOLDER + '/empty.xml')

    def collector

    def setup() {
        collector = new TestResultsCollector(testFolder)
    }

    def 'should collect all specification names in the folder'() {
        given:
        copyFileToDirectory(XML_WITH_ONE_TEST_CASE, testFolder)
        copyFileToDirectory(XML_WITH_TWO_TEST_CASES, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs['spock.damagecontrol.TestResultsCollectorTest1']
        specs['spock.damagecontrol.TestResultsCollectorTest2']
        specs['spock.damagecontrol.AnotherTestResultsCollectorTest']
    }

    def 'should collect all features for the same specification'() {
        given:
        copyFileToDirectory(XML_WITH_TWO_TEST_CASES, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldParseXml']
        specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail']
    }

    def 'should collect failure message for each feature'() {
        given:
        copyFileToDirectory(XML_WITH_TWO_TEST_CASES, testFolder)

        when:
        Map specs = collector.collect().specs

        and:
        Feature feature = specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail']

        then:
        feature.failure.message == 'java.lang.AssertionError: \nExpected: is <true>\n     got: <false>\n'
    }

    def 'should collect duration for each feature'() {
        given:
        copyFileToDirectory(XML_WITH_TWO_TEST_CASES, testFolder)

        when:
        Map specs = collector.collect().specs

        and:
        Feature feature = specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail']

        then:
        feature.duration == '0.001'
    }

    def 'should collect specification duration time'() {
        given:
        copyFileToDirectory(XML_WITH_TWO_TEST_CASES, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].duration == '0.005'
    }

    def 'should collect ignored features'() {
        given:
        copyFileToDirectory(XML_WITH_IGNORED_TEST_CASE, testFolder)

        when:
        Map specs = collector.collect().specs

        and:
        Feature feature = specs['spock.damagecontrol.TestResultsWithIgnoredTestCase'].features['ignored feature']

        then:
        feature.ignored
    }

    def 'should collect skipped features'() {
        given:
        copyFileToDirectory(XML_WITH_IGNORED_TEST_CASE, testFolder)

        when:
        Map specs = collector.collect().specs

        and:
        Feature feature = specs['spock.damagecontrol.TestResultsWithIgnoredTestCase'].features['skipped feature']

        then:
        feature.ignored
    }

    def 'should collect failure details for each feature'() {
        given:
        copyFileToDirectory(XML_WITH_TWO_TEST_CASES, testFolder)

        when:
        Map specs = collector.collect().specs

        and:
        Feature feature = specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail']

        then:
        feature.failure.details.contains('at spock.damagecontrol.TestResultsCollectorTest.shouldFail(TestResultsParserTest.groovy:19)')
    }

    def 'should collect standard output for spec'() {
        given:
        copyFileToDirectory(XML_WITH_SYS_OUT, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs['spock.damagecontrol.TestResultsWithSysOut'].output.standard == 'standard output message'
    }

    def 'should collect results without standard output'() {
        given:
        copyFileToDirectory(XML_WITHOUT_SYS_OUT, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs['spock.damagecontrol.TestResultsWithoutSysOut'].output.standard == ''
    }

    def 'should collect error output for spec'() {
        given:
        copyFileToDirectory(XML_WITH_SYS_OUT, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs['spock.damagecontrol.TestResultsWithSysOut'].output.error == 'error output message'
    }

    def 'should collect results without error output'() {
        given:
        copyFileToDirectory(XML_WITHOUT_SYS_OUT, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs['spock.damagecontrol.TestResultsWithoutSysOut'].output.error == ''
    }

    def 'should collect nothing when result file has no test cases'() {
        given:
        copyFileToDirectory(XML_WITH_NO_TEST_CASE, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs.size() == 0
    }

    def 'should collect nothing when result file is empty'() {
        given:
        copyFileToDirectory(EMPTY, testFolder)

        when:
        Map specs = collector.collect().specs

        then:
        specs.size() == 0
    }
}
