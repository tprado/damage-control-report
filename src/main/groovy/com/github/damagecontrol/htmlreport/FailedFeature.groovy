package com.github.damagecontrol.htmlreport

class FailedFeature extends BaseFeature {

    final failed = true
    final ignored = false
    final result = com.github.damagecontrol.htmlreport.Results.FAILED

    def identifyStepsResult(specName) {
        def errorLine = errorLines(specName).find { startLineNumber <= it && it <= endLineNumber }

        if (errorLine) {
            steps.findAll { it.lineNumber > errorLine }.each { it.result = com.github.damagecontrol.htmlreport.Results.NOT_PERFORMED }
            steps.findAll { it.result == com.github.damagecontrol.htmlreport.Results.UNKNOWN && it.lineNumber < errorLine }.last().result = com.github.damagecontrol.htmlreport.Results.FAILED
            steps.findAll { it.result == com.github.damagecontrol.htmlreport.Results.UNKNOWN }.each { it.result = com.github.damagecontrol.htmlreport.Results.PASSED }
            moveDetailsFromFeatureToStep()
        }
    }

    private errorLines(specName) {
        def lines = []

        def shortName = specName.split('\\.').last()
        def match = details =~ ".*\\(${shortName}\\.groovy\\:([0-9]+)\\)"
        match.each { lines.add(it[1].toInteger()) }

        lines
    }

    private moveDetailsFromFeatureToStep() {
        def failedStep = steps.find { it.result == com.github.damagecontrol.htmlreport.Results.FAILED }
        if (failedStep) {
            failedStep.details = details
            details = ''
        }
    }
}
