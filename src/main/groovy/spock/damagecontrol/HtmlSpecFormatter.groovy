package spock.damagecontrol

import groovy.text.GStringTemplateEngine
import groovy.text.Template

class HtmlSpecFormatter {

    private static final URL SPEC_HTML_URL = HtmlSpecFormatter.class.getResource('/spock/damagecontrol/templates/spec.html')

    private final Spec spec
    private final SpecDefinition specDefinition
    private final Template specHtmlTemplate

    HtmlSpecFormatter(spec, specDefinition) {
        this.spec = spec
        this.specDefinition = specDefinition
        this.specHtmlTemplate = new GStringTemplateEngine().createTemplate(SPEC_HTML_URL)
    }

    def file(baseFolder) {
        return new File(baseFolder.absolutePath + '/' + spec.name + '.html')
    }

    def format() {
        String specDefinitionHtml = new HtmlSpecDefinitionFormatter(spec, specDefinition).format()

        return specHtmlTemplate.make([
                spec_definition: specDefinitionHtml,
                spec_standard_output: spec.output.standard,
                spec_error_output: spec.output.error,
                features: spec.features
        ]).toString()
    }
}
