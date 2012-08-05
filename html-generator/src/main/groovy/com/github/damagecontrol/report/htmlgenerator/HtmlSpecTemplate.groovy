package com.github.damagecontrol.report.htmlgenerator

import groovy.text.GStringTemplateEngine

class HtmlSpecTemplate {

    private static final SPEC_HTML_URL = getResource('/com/github/damagecontrol/htmlreport/templates/spec.html')
    private static final TEMPLATE = new GStringTemplateEngine().createTemplate(SPEC_HTML_URL)

    def generate(spec) {
        TEMPLATE.make([
                spec_standard_output: spec.output.standard,
                spec_error_output: spec.output.error,
                features: spec.features,
                spec: spec
        ]).toString()
    }
}
