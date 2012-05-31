package spock.damagecontrol

class HtmlSpecFormatterTest extends BaseSpec {

    private static final String code = "class SampleSpecTest extends Specification { }"

    private Spec spec
    private SpecDefinition specDefinition = new SpecDefinition(code)
    private HtmlSpecFormatter formatter

    def setup() {
        spec = new Spec('samples.definitions.SampleSpecTest')
        spec.features['feature 1'] = new Feature()
        spec.features['feature 2'] = new Feature()

        spec.output = new SpecOutput('standard output message', 'error output message')
        formatter = new HtmlSpecFormatter(spec, specDefinition)
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
        html =~ /(?s).*<td>feature 1<\/td>.*/
        html =~ /(?s).*<td>feature 2<\/td>.*/
    }
}
