package spock.damagecontrol

import groovy.text.GStringTemplateEngine

class HtmlIndexTemplate {

    static final INDEX_URL = HtmlIndexTemplate.getResource('/spock/damagecontrol/templates/index.html')
    static final TEMPLATE = new GStringTemplateEngine().createTemplate(INDEX_URL)

    def generate(specs) {
        TEMPLATE.make([specs: specs]).toString()
    }
}
