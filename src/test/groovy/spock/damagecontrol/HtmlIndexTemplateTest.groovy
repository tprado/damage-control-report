package spock.damagecontrol

class HtmlIndexTemplateTest extends BaseSpec {

    private List specs

    def setup(){
        given:
        Spec spec1 = new Spec('spec 1')
        spec1.features['feature 1'] = new Feature()
        Spec spec2 = new Spec('spec 2')
        specs = [spec1, spec2]

    }

    def 'should generate list of specifications'() {
        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td class="feature-name">spec 1<\/td>/
        html =~ /(?s)<td class="feature-name">spec 2<\/td>/
    }

    def 'should show number of features for each specification'() {
        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td class="feature-name">spec 1<\/td>\s*<td>1<\/td>/
    }
}
