package spock.damagecontrol

class HtmlSpecFormatterTest extends BaseSpec {

    private static final String code = "class SampleSpecTest extends Specification { }"

    private Spec spec
    private HtmlSpecFormatter formatter

    def setup() {
        spec = new Spec('samples.definitions.SampleSpecTest')
        spec.features['feature 1'] = new Feature()
        spec.features['feature 1'].failed 'error message', 'at SampleSpecificationTest.shouldFail(SampleSpecTest.groovy:14)'
        spec.features['feature 2'] = new Feature()
        spec.sourceCode = code
        spec.output = new SpecOutput('standard output message', 'error output message')
        formatter = new HtmlSpecFormatter(spec)
    }

    def 'should name HTML file based on spec name'() {
        when:
        File htmlFile = formatter.file(new File('.'))

        then:
        htmlFile.name == 'samples.definitions.SampleSpecTest.html'
    }

    def 'should surround spec definition with div'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s)<div id='spec-definition'>.*(SampleSpecTest).*<\/div>/
    }

    def 'should surround standard output with div'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s)<div id='spec-standard-output'>.*(standard output message).*<\/div>/
    }

    def 'should surround error output with div'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s)<div id='spec-error-output'>.*(error output message).*<\/div>/
    }

    def 'should list all features'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<td class="feature-name">feature 1<\/td>.*/
        html =~ /(?s).*<td class="feature-name">feature 2<\/td>.*/
    }

    def 'should show passed for successful feature'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<td class="feature-name">feature 2<\/td>\s*<td class="feature-result">passed<\/td>.*/
    }

    def 'should show failed for unsuccessful feature'() {
        when:
        String html = formatter.format()

        then:
        html =~ /(?s).*<td class="feature-name">feature 1<\/td>\s*<td class="feature-result">failed<\/td>.*/
    }
}
