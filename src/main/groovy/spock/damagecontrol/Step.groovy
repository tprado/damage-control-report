package spock.damagecontrol

import static spock.damagecontrol.Results.UNKNOWN

class Step {

    def type
    def description
    def lineNumber
    def result = UNKNOWN
    def details
}
