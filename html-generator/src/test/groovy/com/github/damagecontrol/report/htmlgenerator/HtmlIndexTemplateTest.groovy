package com.github.damagecontrol.report.htmlgenerator

class HtmlIndexTemplateTest extends BaseSpec {

    HtmlIndexTemplate indexTemplate
    def specs

    def setup() {
        given:
        Spec spec1 = new Spec(name: 'Spec1')
        spec1.duration = '0.155'
        spec1.passed('feature 1')
        spec1.failed('feature 2')
        spec1.features.'feature 2'.details = 'error detail'
        spec1.skipped('feature 3')

        Spec spec2 = new Spec(name: 'Spec2')

        specs = [spec1, spec2]

        indexTemplate = new HtmlIndexTemplate()
    }

    def 'should generate list of specifications as links'() {
        when:
        String html = indexTemplate.generate(specs)

        then:
        //TODO remove class and ignore this part in the regular expression
        html =~ /(?s)<td id="Spec1"><a href="Spec1.html" class="result_failed">Spec1<\/a><\/td>/
        html =~ /(?s)<td id="Spec2"><a href="Spec2.html" class="result_skipped">Spec2<\/a><\/td>/
    }

    def 'should show number of features for each specification'() {
        when:
        String html = indexTemplate.generate(specs)

        then:
        html =~ /(?s)<td id="Spec1_featureCount">3<\/td>/
    }

    def 'should show number of failed features for each specification'() {
        when:
        String html = indexTemplate.generate(specs)

        then:
        html =~ /(?s)<td id="Spec1_failedFeatureCount">1<\/td>/
    }

    def 'should show number of skipped features for each specification'() {
        when:
        String html = indexTemplate.generate(specs)

        then:
        html =~ /(?s)<td id="Spec1_skippedFeatureCount">1<\/td>/
    }

    def 'should show result for each specification'() {
        when:
        String html = indexTemplate.generate(specs)

        then:
        //TODO remove class and ignore this part in the regular expression
        html =~ /(?s)<td id="Spec1_result" class="result_failed">failed<\/td>/
    }

    def 'should show duration for each specification'() {
        when:
        String html = indexTemplate.generate(specs)

        then:
        html =~ /(?s)<td id="Spec1_duration">0.155s<\/td>/
    }
}
