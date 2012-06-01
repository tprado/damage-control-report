package spock.damagecontrol

class Feature {

    def failure
    def ignored

    def fail(message, details) {
        failure = new Failure()
        failure.message = message
        failure.details = details
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
