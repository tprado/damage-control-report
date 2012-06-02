package spock.damagecontrol

import groovy.text.GStringTemplateEngine

class HtmlIndexTemplate {

    def static final INDEX_URL = Report.class.getResource('/spock/damagecontrol/templates/index.html')

    def final indexTemplate
    def final specs

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
