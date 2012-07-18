package spock.damagecontrol

import static org.apache.commons.io.FileUtils.copyFileToDirectory

import org.apache.commons.io.IOUtils

class XmlFileReaderTest extends BaseFileHandlingSpec {

    static final SAMPLES = 'src/test/resources/samples/results'

    static final XML_WITH_ONE_TEST_CASE = new File(SAMPLES + '/TEST-spock.damagecontrol.TestResultsParserTest.xml')
    static final XML_WITH_TWO_TEST_CASES = new File(SAMPLES + '/TEST-spock.damagecontrol.TestResultsWith2TestCases.xml')

    def xmlFileReader

    def setup() {
        xmlFileReader = new XmlFileReader(inputFolder: testFolder)
    }

    def 'should read all XML files in the folder'() {
        given:
        copyFileToDirectory(XML_WITH_ONE_TEST_CASE, testFolder)
        copyFileToDirectory(XML_WITH_TWO_TEST_CASES, testFolder)

        int count = 0

        when:
        xmlFileReader.forEach { count++ }

        then:
        count == 2
    }

    def 'should provide reader for each XML file in the folder'() {
        given:
        copyFileToDirectory(XML_WITH_ONE_TEST_CASE, testFolder)

        def lines

        when:
        xmlFileReader.forEach { lines = IOUtils.readLines(it) }

        then:
        lines[1] =~ /spock.damagecontrol.TestResultsCollectorTest/
    }

    def 'should always close each reader after used'() {
        given:
        copyFileToDirectory(XML_WITH_ONE_TEST_CASE, testFolder)

        when:
        def reader
        xmlFileReader.forEach {
            reader = it
            throw new IOException()
        }
        reader.read()

        then:
        thrown(IOException)
    }
}
