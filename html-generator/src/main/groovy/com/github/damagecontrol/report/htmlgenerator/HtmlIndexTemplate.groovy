package com.github.damagecontrol.report.htmlgenerator

import groovy.text.GStringTemplateEngine

class HtmlIndexTemplate {

    private static final INDEX_URL = getResource('/com/github/damagecontrol/htmlreport/templates/index.html')
    private static final TEMPLATE = new GStringTemplateEngine().createTemplate(INDEX_URL)

    def generate(specs) {
        TEMPLATE.make([specs: specs]).toString()
    }
}
