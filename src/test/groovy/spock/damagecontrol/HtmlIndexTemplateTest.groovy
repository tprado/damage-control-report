package spock.damagecontrol

class HtmlIndexTemplateTest extends BaseSpec {

    def specs

    def setup() {
        given:
        Spec spec1 = new Spec('Spec1')
        spec1.duration = '0.155'
        spec1.features['feature 1'] = new Feature()
        spec1.features['feature 2'] = new Feature()
        spec1.features['feature 2'].fail 'error', 'error detail'
        spec1.features['feature 3'] = new Feature()
        spec1.features['feature 3'].ignore()
        Spec spec2 = new Spec('Spec2')
        specs = [spec1, spec2]

    }

    def 'should generate list of specifications'() {
        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td class="spec-name">Spec1<\/td>/
        html =~ /(?s)<td class="spec-name">Spec2<\/td>/
    }

    def 'should show number of features for each specification'() {
        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td id="Spec1_featureCount">3<\/td>/
    }

    def 'should show number of failed features for each specification'() {
        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td id="Spec1_failedFeatureCount">1<\/td>/
    }

    def 'should show number of skipped features for each specification'() {
        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td id="Spec1_skippedFeatureCount">1<\/td>/
    }

    def 'should show result for each specification'() {
        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td id="Spec1_result">failed<\/td>/
    }

    def 'should show duration for each specification'() {
        when:
        String html = new HtmlIndexTemplate(specs).generate()

        then:
        html =~ /(?s)<td id="Spec1_duration">0.155s<\/td>/
    }
}
