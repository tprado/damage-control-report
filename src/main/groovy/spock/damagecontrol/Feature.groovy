package spock.damagecontrol

class Feature {

    def failure
    def ignored = false
    def duration

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
            return 'failed'
        }
        return 'passed'
    }

    def getFailed() {
        result == 'failed'
    }
}
