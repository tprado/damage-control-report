package spock.damagecontrol

class Feature {

    def failure

    def failed(message, details) {
        failure = new Failure()
        failure.message = message
        failure.details = details
    }
}
