package spock.damagecontrol

import groovy.text.GStringTemplateEngine

class HtmlSpecTemplate {

    static final SPEC_HTML_URL = HtmlSpecTemplate.getResource('/spock/damagecontrol/templates/spec.html')

    def spec

    def generate() {
        def specHtmlTemplate = new GStringTemplateEngine().createTemplate(SPEC_HTML_URL)

        specHtmlTemplate.make([
                spec_standard_output: spec.output.standard,
                spec_error_output: spec.output.error,
                features: spec.features,
                spec: spec
        ]).toString()
    }
}
