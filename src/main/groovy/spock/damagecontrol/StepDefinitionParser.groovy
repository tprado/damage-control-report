package spock.damagecontrol

class StepDefinitionParser {

    def parse(sourceCode) {
        def steps = []

        def match = sourceCode =~ /(?m)^\s*(given|when|then|and|expect|setup|cleanup)\s*:\s*('[^\n]*'|"[^\n]*"|$)/
        match.each {
            steps.add(new Step(type: it[1], description: it[2]))
        }

        steps
    }
}
