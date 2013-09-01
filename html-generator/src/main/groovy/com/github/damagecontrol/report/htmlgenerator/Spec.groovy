package com.github.damagecontrol.report.htmlgenerator

import static com.github.damagecontrol.report.htmlgenerator.Results.FAILED
import static com.github.damagecontrol.report.htmlgenerator.Results.PASSED
import static com.github.damagecontrol.report.htmlgenerator.Results.SKIPPED

class Spec {

    final output = new SpecOutput()
    final features = [:]

    def name
    def duration

    def passed(featureName) {
        features[featureName] = new PassedFeature(name: featureName)
    }

    def skipped(featureName) {
        features[featureName] = new SkippedFeature(name: featureName)
    }

    def failed(featureName) {
        features[featureName] = new FailedFeature(name: featureName)
    }

    @SuppressWarnings('CatchException')
    def parseEachFeatureDefinition(sourceCode) {
        String annotatedSourceCode = new LineNumberAnnotator().annotate(sourceCode)
        features.each {featureName, feature ->
            try {
                feature.parseDefinition(annotatedSourceCode)
                if (feature.failed) {
                    feature.identifyStepsResult(name)
                }
            } catch (Exception e) {
                throw new ParseFeatureDefinitionException(name, featureName, e)
            }
        }
    }

    def getFeatureCount() {
        features.size()
    }

    def getFailedFeatureCount() {
        features.count { name, feature -> feature.failed }
    }

    def getSkippedFeatureCount() {
        features.count { name, feature -> feature.ignored }
    }

    def getResult() {
        if (failedFeatureCount > 0) {
            return FAILED
        }
        if (skippedFeatureCount == featureCount) {
            return SKIPPED
        }
        PASSED
    }

    def getFailedResult() {
        result == FAILED
    }

    def getSkippedResult() {
        result == SKIPPED
    }

    int getSuccessPercentage() {
        def successfulFeatureCount = featureCount - failedFeatureCount
        float successPercentage = successfulFeatureCount / featureCount * 100
        successPercentage.round(0)
    }
}
