package spock.damagecontrol

import static spock.damagecontrol.Results.FAILED
import static spock.damagecontrol.Results.NOT_PERFORMED
import static spock.damagecontrol.Results.PASSED
import static spock.damagecontrol.Results.UNKNOWN

class FailedFeature extends BaseFeature {

    final failed = true
    final ignored = false
    final result = FAILED

    def identifyStepsResult(specName) {
        def errorLine = errorLines(specName).find { startLineNumber <= it && it <= endLineNumber }

        if (errorLine) {
            steps.findAll { it.lineNumber > errorLine }.each { it.result = NOT_PERFORMED }
            steps.findAll { it.result == UNKNOWN && it.lineNumber < errorLine }.last().result = FAILED
            steps.findAll { it.result == UNKNOWN }.each { it.result = PASSED }
        }
    }

    private errorLines(specName) {
        def lines = []

        def shortName = specName.split('\\.').last()
        def match = details =~ ".*\\(${shortName}\\.groovy\\:([0-9]+)\\)"
        match.each { lines.add(it[1].toInteger()) }

        lines
    }
}
