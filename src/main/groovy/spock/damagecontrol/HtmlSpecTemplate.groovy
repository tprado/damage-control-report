package spock.damagecontrol

import groovy.text.GStringTemplateEngine

class HtmlSpecTemplate {

    static final SPEC_HTML_URL = HtmlSpecTemplate.getResource('/spock/damagecontrol/templates/spec.html')

    final spec
    final specHtmlTemplate

    HtmlSpecTemplate(spec) {
        this.spec = spec
        this.specHtmlTemplate = new GStringTemplateEngine().createTemplate(SPEC_HTML_URL)
    }

    def file(baseFolder) {
        new File(baseFolder.absolutePath + '/' + spec.name + '.html')
    }

    def generate() {

        specHtmlTemplate.make([
                spec_standard_output: spec.output.standard,
                spec_error_output: spec.output.error,
                features: spec.features,
                spec: spec
        ]).toString()
    }
}
