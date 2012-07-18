package spock.damagecontrol

class TestResultsCollectorTest extends BaseSpec {

    TestResultsCollector collector

    def setup() {
        collector = new TestResultsCollector()
    }

    def 'should collect all features for the same specification'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="spock.damagecontrol.AnotherTestResultsCollectorTest" name="shouldParseXml" time="0.002" />
                <testcase classname="spock.damagecontrol.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001" />
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldParseXml']
        specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail']
    }

    def 'should collect failure message for each feature'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="spock.damagecontrol.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001">
                    <failure message="java.lang.AssertionError: Expected: is &lt;true&gt; got: &lt;false&gt;" />
                </testcase>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        Feature feature = specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail']

        then:
        feature.failure.message == 'java.lang.AssertionError: Expected: is <true> got: <false>'
    }

    def 'should collect duration for each feature'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="spock.damagecontrol.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        Feature feature = specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail']

        then:
        feature.duration == '0.001'
    }

    def 'should collect feature name'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="spock.damagecontrol.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail'].name == 'shouldFail'
    }

    def 'should collect specification duration time'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="spock.damagecontrol.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].duration == '0.005'
    }

    def 'should collect ignored features'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.TestResultsWithIgnoredTestCase" time="0.005">
                <ignored-testcase classname="spock.damagecontrol.TestResultsWithIgnoredTestCase" name="ignored feature" time="0.0" />
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        Feature feature = specs['spock.damagecontrol.TestResultsWithIgnoredTestCase'].features['ignored feature']

        then:
        feature.ignored
    }

    def 'should collect skipped features'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.TestResultsWithIgnoredTestCase" time="0.005">
                <testcase classname="spock.damagecontrol.TestResultsWithIgnoredTestCase" name="skipped feature" time="0.126">
                    <skipped/>
                </testcase>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        Feature feature = specs['spock.damagecontrol.TestResultsWithIgnoredTestCase'].features['skipped feature']

        then:
        feature.ignored
    }

    def 'should collect failure details for each feature'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.AnotherTestResultsCollectorTest" time="0.005">
                <testcase classname="spock.damagecontrol.AnotherTestResultsCollectorTest" name="shouldFail" time="0.001">
                    <failure message="failure message">
                        <![CDATA[ TestResultsCollectorTest.shouldFail(TestResultsParserTest.groovy:19) ]]>
                    </failure>
                </testcase>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        and:
        Feature feature = specs['spock.damagecontrol.AnotherTestResultsCollectorTest'].features['shouldFail']

        then:
        feature.failure.details == 'TestResultsCollectorTest.shouldFail(TestResultsParserTest.groovy:19)'
    }

    def 'should collect standard output for spec'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.TestResultsWithSysOut" time="0.005">
                <testcase classname="spock.damagecontrol.TestResultsWithSysOut" name="feature name" time="0.001"/>
                <system-out><![CDATA[ standard output message ]]></system-out>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['spock.damagecontrol.TestResultsWithSysOut'].output.standard == 'standard output message'
    }

    def 'should collect results without standard output'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.TestResultsWithoutSysOut" time="0.005">
                <testcase classname="spock.damagecontrol.TestResultsWithoutSysOut" name="feature name" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['spock.damagecontrol.TestResultsWithoutSysOut'].output.standard == ''
    }

    def 'should collect error output for spec'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.TestResultsWithSysOut" time="0.005">
                <testcase classname="spock.damagecontrol.TestResultsWithSysOut" name="feature name" time="0.001"/>
                <system-err><![CDATA[ error output message ]]></system-err>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['spock.damagecontrol.TestResultsWithSysOut'].output.error == 'error output message'
    }

    def 'should collect results without error output'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.TestResultsWithoutSysOut" time="0.005">
                <testcase classname="spock.damagecontrol.TestResultsWithoutSysOut" name="feature name" time="0.001"/>
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))
        Map specs = collector.results.specs

        then:
        specs['spock.damagecontrol.TestResultsWithoutSysOut'].output.error == ''
    }

    def 'should collect no feature when result file has no test cases'() {
        given:
        String xml = '''<?xml version="1.0" encoding="UTF-8"?>
            <testsuite name="spock.damagecontrol.NoTests" time="0.005">
            </testsuite>'''

        when:
        collector.collect(new StringReader(xml))

        then:
        collector.results.spec('spock.damagecontrol.NoTests').features.size() == 0
    }
}
