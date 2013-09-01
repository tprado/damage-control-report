package com.github.damagecontrol.report.htmlgenerator

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
        int count = 0
        specs.each { specName, spec ->
            if (spec.result == 'skipped') {
                count += 1
            }
        }
        count
    }

    def getFailedSpecCount() {
        int count = 0
        specs.each { specName, spec ->
            if (spec.result == 'failed') {
                count += 1
            }
        }
        count
    }

    def getResult() {
        if (failedSpecCount > 0) {
            return 'failed'
        }
        if (skippedSpecCount == specCount) {
            return 'skipped'
        }
        'passed'
    }

    def getSuccessPercentage() {
        def successfulSpecCount = specCount - failedSpecCount
        float successPercentage = successfulSpecCount / specCount * 100
        successPercentage.round(0)
    }
}
