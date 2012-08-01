package com.github.damagecontrol.htmlreport

class StepDefinitionParser {

    static final STEP = /(?m)^#([0-9]*)#\s*(given|when|then|and|expect|setup|cleanup)\s*:\s*('[^\n]*'|"[^\n]*"|$)/

    def parse(lineNumberAnnotatedSourceCode) {
        def steps = []

        def match = lineNumberAnnotatedSourceCode =~ STEP
        match.each { steps.add(new Step(type: it[2], description: it[3], lineNumber: it[1].toInteger())) }

        steps
    }
}
