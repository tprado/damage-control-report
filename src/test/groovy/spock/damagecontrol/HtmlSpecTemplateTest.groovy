package spock.damagecontrol

class HtmlSpecTemplateTest extends BaseSpec {

    static final CODE = 'class SampleSpecTest extends Specification { }'

    def spec
    def template

    def setup() {
        spec = new Spec('samples.definitions.SampleSpecTest')
        spec.features['feature 1'] = new Feature()
        spec.features['feature 1'].duration = '0.250'
        spec.features['feature 1'].fail 'error', 'at SampleSpecificationTest.shouldFail(SampleSpecTest.groovy:14)'
        spec.features['feature 2'] = new Feature()
        spec.sourceCode = CODE
        spec.output = new SpecOutput('standard output message', 'error output message')
        template = new HtmlSpecTemplate(spec)
    }

    def 'should name HTML file based on spec name'() {
        when:
        File htmlFile = template.file(new File('.'))

        then:
        htmlFile.name == 'samples.definitions.SampleSpecTest.html'
    }

    def 'should surround spec definition with div'() {
        when:
        String html = template.generate()

        then:
        html =~ /(?s)<div id='spec-definition'>.*(SampleSpecTest).*<\/div>/
    }

    def 'should surround standard output with div'() {
        when:
        String html = template.generate()

        then:
        html =~ /(?s)<div id='spec-standard-output'>.*(standard output message).*<\/div>/
    }

    def 'should surround error output with div'() {
        when:
        String html = template.generate()

        then:
        html =~ /(?s)<div id='spec-error-output'>.*(error output message).*<\/div>/
    }

    def 'should list all features'() {
        when:
        String html = template.generate()

        then:
        html =~ /(?s).*<td class="feature-name">feature 1<\/td>.*/
        html =~ /(?s).*<td class="feature-name">feature 2<\/td>.*/
    }

    def 'should show result for feature'() {
        when:
        String html = template.generate()

        then:
        html =~ /(?s).*<td id="feature 1_result">failed<\/td>.*/
    }

    def 'should show spec duration'() {
        when:
        String html = template.generate()

        then:
        html =~ /(?s).*<td id="feature 1_duration">0.250s<\/td>.*/
    }
}
