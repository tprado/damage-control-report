package spock.damagecontrol

class Feature {

    static final FAILED = 'failed'

    final featureDefinitionParser = new FeatureDefinitionParser()
    final featureStepsParser = new StepDefinitionParser()

    def name
    def failure
    def ignored = false
    def duration
    def steps = []

    def fail(message, details) {
        failure = new Failure()
        failure.message = message
        failure.details = details
    }

    def ignore() {
        ignored = true
    }

    def getResult() {
        if (ignored) {
            return 'skipped'
        }
        if (failure) {
            return FAILED
        }
        'passed'
    }

    def getFailed() {
        result == FAILED
    }

    def parseDefinition(specSourceCode) {
        steps = featureStepsParser.parse(featureDefinitionParser.parse(name, specSourceCode))
    }
}
