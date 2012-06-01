package spock.damagecontrol

import groovy.text.Template
import groovy.text.GStringTemplateEngine

class HtmlIndexTemplate {

    private static final URL INDEX_URL = Report.class.getResource('/spock/damagecontrol/templates/index.html')

    private final Template indexTemplate
    private final List specs

    HtmlIndexTemplate(specs) {
        this.specs = specs
        this.indexTemplate = new GStringTemplateEngine().createTemplate(INDEX_URL)
    }

    def generate() {
        return indexTemplate.make([
                specs: specs
        ]).toString()
    }
}
