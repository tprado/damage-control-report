package com.github.damagecontrol.htmlreport

import groovy.text.GStringTemplateEngine

class HtmlIndexTemplate {

    private static final INDEX_URL = HtmlIndexTemplate.getResource('/spock/damagecontrol/templates/index.html')
    private static final TEMPLATE = new GStringTemplateEngine().createTemplate(INDEX_URL)

    def generate(specs) {
        TEMPLATE.make([specs: specs]).toString()
    }
}
