package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.FAILED
import static com.github.damagecontrol.report.htmlgenerator.Results.PASSED
import static com.github.damagecontrol.report.htmlgenerator.Results.SKIPPED

class TestResults {

    final specs = [:]

    def getSpecList() {
        specs.values().toList()
    }

    def spec(specName) {
        specs[specName] = specs[specName] ?: new Spec(name: specName)
    }

    def getSpecCount() {
        specs.size()
    }

    def getSkippedSpecCount() {
        specs.count { name, spec -> spec.skippedResult }
    }

    def getFailedSpecCount() {
        specs.count { name, spec -> spec.failedResult }
    }

    def getResult() {
        if (failedSpecCount > 0) {
            return FAILED
        }
        if (skippedSpecCount == specCount) {
            return SKIPPED
        }
        PASSED
    }

    def getSuccessPercentage() {
        def successfulSpecCount = specCount - failedSpecCount
        float successPercentage = successfulSpecCount / specCount * 100
        successPercentage.round(0)
    }
}
