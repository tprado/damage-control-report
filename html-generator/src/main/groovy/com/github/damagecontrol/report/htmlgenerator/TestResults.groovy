package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.*
import static java.lang.Float.parseFloat

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

    def getDuration() {
        float duration = 0
        specs.each { name, spec ->
            duration += parseDuration(spec.duration)
        }
        duration.toString()
    }

    @SuppressWarnings('CatchException')
    private float parseDuration(duration) {
        try {
            parseFloat(duration)
        } catch (Exception e) {
            0
        }
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
