package spock.damagecontrol

import static org.apache.commons.io.FileUtils.readFileToString

class Spec {

    static final String FILE_SEPARATOR = '/'
    def name
    def features = [:]
    def output
    def duration

    def readDefinitionFrom(specsFolder) {
        parseEachFeatureDefinition(readFileToString(groovyFile(specsFolder)))
    }

    def groovyFile(baseFolder) {
        new File(baseFolder.absolutePath + FILE_SEPARATOR + name.replaceAll(/\./, FILE_SEPARATOR) + '.groovy')
    }

    def parseEachFeatureDefinition(sourceCode) {
        features.each {featureName, feature ->
            feature.parseDefinition(sourceCode)
        }
    }

    def errorLines() {
        def shortNameRegex = '.*\\(' + name.split('\\.').last() + '\\.groovy\\:([0-9]+)\\)'
        List lines = []

        features.each { featureName, feature ->
            if (feature.failure) {
                def match = feature.failure.details =~ shortNameRegex
                match.each { lines.add it[1].toInteger() }
            }
        }

        lines
    }

    def getFeatureCount() {
        features.size()
    }

    def getFailedFeatureCount() {
        int count = 0
        features.each { featureName, feature ->
            if (feature.failed) {
                count += 1
            }
        }
        count
    }

    def getSkippedFeatureCount() {
        int count = 0
        features.each { featureName, feature ->
            if (feature.ignored) {
                count += 1
            }
        }
        count
    }

    def getResult() {
        if (failedFeatureCount > 0) {
            return 'failed'
        }
        if (skippedFeatureCount == featureCount) {
            return 'skipped'
        }
        'passed'
    }

    int getSuccessPercentage() {
        def successfulFeatureCount = featureCount - failedFeatureCount
        float successPercentage = successfulFeatureCount/featureCount * 100
        successPercentage.round(0)
    }
}
