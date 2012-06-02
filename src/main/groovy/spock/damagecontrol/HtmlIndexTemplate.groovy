package spock.damagecontrol

import groovy.text.GStringTemplateEngine

class HtmlIndexTemplate {

    static final INDEX_URL = Report.getResource('/spock/damagecontrol/templates/index.html')

    final indexTemplate
    final specs

    HtmlIndexTemplate(specs) {
        this.specs = specs
        this.indexTemplate = new GStringTemplateEngine().createTemplate(INDEX_URL)
    }

    def generate() {
        indexTemplate.make([specs: specs]).toString()
    }
}
