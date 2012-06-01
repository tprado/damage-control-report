package spock.damagecontrol

import groovy.text.GStringTemplateEngine
import groovy.text.Template

class HtmlSpecTemplate {

    private static final URL SPEC_HTML_URL = HtmlSpecTemplate.class.getResource('/spock/damagecontrol/templates/spec.html')

    private final Spec spec
    private final Template specHtmlTemplate

    HtmlSpecTemplate(spec) {
        this.spec = spec
        this.specHtmlTemplate = new GStringTemplateEngine().createTemplate(SPEC_HTML_URL)
    }

    def file(baseFolder) {
        return new File(baseFolder.absolutePath + '/' + spec.name + '.html')
    }

    def generate() {
        String specDefinitionHtml = new HtmlSpecDefinitionFormatter(spec).format()

        return specHtmlTemplate.make([
                spec_definition: specDefinitionHtml,
                spec_standard_output: spec.output.standard,
                spec_error_output: spec.output.error,
                features: spec.features
        ]).toString()
    }
}
