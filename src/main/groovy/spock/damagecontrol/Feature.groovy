package spock.damagecontrol

class Feature {

    def failure
    def ignored

    def failed(message, details) {
        failure = new Failure()
        failure.message = message
        failure.details = details
    }
}
