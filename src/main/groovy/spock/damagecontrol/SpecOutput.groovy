package spock.damagecontrol

class SpecOutput {

    def standard
    def error

    SpecOutput(standard, error) {
        this.standard = standard
        this.error = error
    }
}
