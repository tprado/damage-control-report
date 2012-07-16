package spock.damagecontrol

import groovy.text.GStringTemplateEngine

class HtmlIndexTemplate {

    static final INDEX_URL = Report.getResource('/spock/damagecontrol/templates/index.html')

    def specs

    def generate() {
        new GStringTemplateEngine().createTemplate(INDEX_URL).make([specs: specs]).toString()
    }
}
