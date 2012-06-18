package spock.damagecontrol

class Feature {

    static final FAILED = 'failed'

    def failure
    def ignored = false
    def duration
    def sourceCode = ''

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
}
