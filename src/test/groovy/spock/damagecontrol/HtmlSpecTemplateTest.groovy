package spock.damagecontrol

import static spock.damagecontrol.Results.FAILED
import static spock.damagecontrol.Results.PASSED

@SuppressWarnings('LineLength')
class HtmlSpecTemplateTest extends BaseSpec {

    HtmlSpecTemplate template
    Spec spec

    def setup() {
        spec = new Spec(name: 'samples.definitions.SampleSpecTest')
        spec.output.standard = 'standard output message'
        spec.output.error = 'error output message'
        spec.duration = '0.355'

        spec.failed('feature 1')
        spec.features.'feature 1'.duration = '0.250'
        spec.features.'feature 1'.details = 'at SampleSpecificationTest.shouldFail(SampleSpecTest.groovy:14)'
        spec.features.'feature 1'.steps.add(new Step(type: 'expect', description: '"something"', result: FAILED, details: 'step failure details'))

        spec.passed('feature 2')
        spec.features.'feature 2'.steps.add(new Step(type: 'expect', description: '"something"', result: PASSED))

        template = new HtmlSpecTemplate()
    }

    def 'should surround standard output with div'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s)<div id='spec-standard-output'>.*(standard output message).*<\/div>/
    }

    def 'should surround error output with div'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s)<div id='spec-error-output'>.*(error output message).*<\/div>/
    }

    def 'should list all features'() {
        when:
        String html = template.generate(spec)

        then:
        //TODO remove class and ignore this part in the regular expression
        html =~ /(?s)<td id="feature 1" class="clickable result_FAILED" expand="feature_0_steps">feature 1<\/td>/
        html =~ /(?s)<td id="feature 2" class="clickable result_PASSED" expand="feature_1_steps">feature 2<\/td>/
    }

    def 'should show result for feature'() {
        when:
        String html = template.generate(spec)

        then:
        //TODO remove class and ignore this part in the regular expression
        html =~ /(?s).*<td id="feature 1_result" class="result_FAILED">failed<\/td>.*/
    }

    def 'should show spec duration'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s).*<td id="feature 1_duration">0.250s<\/td>.*/
    }

    def 'should show number of features'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s).*<div class="summaryCounter" id="featureCount">2.*<\/div>/
    }

    def 'should show number of failed features'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s).*<div class="summaryCounter" id="failedFeatureCount">1.*<\/div>/
    }

    def 'should show number of skipped features'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s).*<div class="summaryCounter" id="skippedFeatureCount">0.*<\/div>/
    }

    def 'should show duration for specification'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s).*<div class="summaryCounter" id="specDuration">0.355s.*<\/div>/
    }

    def 'should show success percentage'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s).*<div id="successPercentage">50%.*<\/div>/
    }

    def 'should show feature steps'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s)<div class="steps">\s*expect "something" <span class="clickable PASSED" expand="feature_1_details">passed<\/span><br\/>\s*<\/div>/
    }

    def 'should show step failure details'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s)<div class="details" expandable="feature_0_details"><pre>step failure details<\/pre><\/div>/
    }

    def 'should show feature failure details'() {
        when:
        String html = template.generate(spec)

        then:
        html =~ /(?s)<div class="details" expandable="feature_0_details"><pre>at SampleSpecificationTest.shouldFail\(SampleSpecTest.groovy:14\)<\/pre><\/div>/
    }
}
