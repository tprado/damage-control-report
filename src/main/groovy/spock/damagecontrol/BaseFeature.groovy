package spock.damagecontrol

import static java.lang.Integer.parseInt

abstract class BaseFeature {

    final steps = []

    def name
    def duration
    def startLineNumber = 0
    def endLineNumber = 0

    private final featureDefinitionParser = new FeatureDefinitionParser()
    private final featureStepsParser = new StepDefinitionParser()

    def parseDefinition(lineNumberAnnotatedSourceCode) {
        String featureDefinition = featureDefinitionParser.parse(name, lineNumberAnnotatedSourceCode)
        setLineNumbers(featureDefinition)
        steps.addAll(featureStepsParser.parse(featureDefinition))
    }

    private setLineNumbers(featureDefinition) {
        def match = featureDefinition =~ /(?m)^#([0-9]*)#/
        if (match.count > 0) {
            startLineNumber = parseInt(match[0][1])
            endLineNumber = parseInt(match[match.count - 1][1])
        }
    }
}
