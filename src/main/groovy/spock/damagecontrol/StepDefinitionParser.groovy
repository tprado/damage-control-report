package spock.damagecontrol

import static java.lang.Integer.parseInt

class StepDefinitionParser {

    static final STEP = /(?m)^#([0-9]*)#\s*(given|when|then|and|expect|setup|cleanup)\s*:\s*('[^\n]*'|"[^\n]*"|$)/

    def parse(lineNumberAnnotatedSourceCode) {
        def steps = []

        def match = lineNumberAnnotatedSourceCode =~ STEP
        match.each {
            steps.add(new Step(type: it[2], description: it[3], lineNumber: parseInt(it[1])))
        }

        steps
    }
}
