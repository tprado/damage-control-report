package spock.damagecontrol;

public class HtmlSpecDefinitionFormatterTest extends BaseSpec {

    private static final String code = """package samples.definitions

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

    private HtmlSpecDefinitionFormatter formatter
    private Spec spec

    def setup() {
        spec = new Spec('samples.definitions.SampleSpecTest')
        spec.features['feature 1'] = new Feature()
        spec.features['feature 1'].failed 'error message', 'at SampleSpecificationTest.shouldFail(SampleSpecTest.groovy:14)'
        spec.sourceCode = code
        formatter = new HtmlSpecDefinitionFormatter(spec)
    }

    def 'should identify "class" as reserved word'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='reserved'>class<\/span>.*/
    }

    def 'should identify "package" as reserved word'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='reserved'>package<\/span>.*/
    }

    def 'should identify "def" as reserved word'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='reserved'>def<\/span>.*/
        html =~ /(?s).*samples\.definitions.*/
    }

    def 'should identify "extends" as reserved word'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='reserved'>extends<\/span>.*/
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

    def 'should identify lines'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<span class='line-number'>1<\/span>.*package.* samples.*/
    }

    def 'should indicate line where error occurred'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?m)<span class='error'>.*'some condition'.*<\/span>$/
    }
}
