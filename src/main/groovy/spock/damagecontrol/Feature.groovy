package spock.damagecontrol

class Feature {

    static final FAILED = 'failed'

    def failure
    def ignored = false
    def duration
    def sourceCode = ''
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

    def setSourceCode(sourceCode) {
        this.sourceCode = sourceCode
        readBlocks()
    }

    def readBlocks() {
        def match = sourceCode =~ /(?m)^\s*(given|when|then|and|expect|setup|cleanup)\s*:\s*('[^\n]*'|"[^\n]*"|$)/
        match.each {
            Step step = new Step()
            step.type = it[1]
            step.description = it[2]
            steps.add(step)
        }
    }
}
