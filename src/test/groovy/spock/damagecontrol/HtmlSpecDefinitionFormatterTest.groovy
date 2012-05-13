package spock.damagecontrol

class HtmlSpecDefinitionFormatterTest extends BaseSpec {

    private static final String code = """
package samples

/* Comments */
class SampleSpecTest extends Specification {

    def 'feature 1'() { /* F1 */
        given: 'given block'
        println 'given statement'

        when: 'when block'
        println 'when statement'

        then: 'then block'
        'some condition'
    }

    def "feature 2"() {
        given: "given block 2"
        println "given statement 2"

        when: "when block 2"
        println "when statement 2"

        then: "then block 2"
        "some condition 2"
    }
}
"""

    private Spec spec = new Spec('samples.SampleSpecTest')

    private SpecDefinition specDefinition = new SpecDefinition(code)

    private HtmlSpecDefinitionFormatter formatter = new HtmlSpecDefinitionFormatter(spec, specDefinition)

    def 'should name HTML file based on spec name'() {
        when:
        File htmlFile = formatter.file(new File('.'))

        then:
        htmlFile.name == 'samples.SampleSpecTest.html'
    }

    def 'should surround spec definition with div'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s)<div id='spec-definition'>.*(SampleSpecTest).*<\/div>/
    }

    def 'should identify single line comments'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='comments'>\Q\/* Comments *\/\E<\/span>.*/
    }

    def 'should identify single quote string literals'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='string-literal'>'feature 1'<\/span>.*/
    }

    def 'should identify double quote string literals'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='string-literal'>"feature 2"<\/span>.*/
    }

    def 'should identify line separators'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*package samples<br\/>.*/
    }

    def 'should identify lines'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='line-number'>2<\/span>package samples.*/
    }

    def 'should keep indentation'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*\Q&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;when:\E.*/
    }

    def 'should add trailing white space'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='line-number'>29<\/span>\Q&nbsp;\E.*/
    }
}
