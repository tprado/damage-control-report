package spock.damagecontrol

class HtmlIndexTemplateTest extends BaseSpec {

    def 'should generate list of specifications'() {
        given:
        Spec spec1 = new Spec('spec 1')
        Spec spec2 = new Spec('spec 2')
        List specs = [spec1, spec2]

        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td class="feature-name">spec 1<\/td>/
        html =~ /(?s)<td class="feature-name">spec 2<\/td>/
    }
}
