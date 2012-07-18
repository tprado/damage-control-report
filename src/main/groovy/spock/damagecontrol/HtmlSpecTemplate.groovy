package spock.damagecontrol

import groovy.text.GStringTemplateEngine

class HtmlSpecTemplate {

    static final SPEC_HTML_URL = HtmlSpecTemplate.getResource('/spock/damagecontrol/templates/spec.html')
    static final TEMPLATE = new GStringTemplateEngine().createTemplate(SPEC_HTML_URL)

    def generate(spec) {
        TEMPLATE.make([
                spec_standard_output: spec.output.standard,
                spec_error_output: spec.output.error,
                features: spec.features,
                spec: spec
        ]).toString()
    }
}
