package spock.damagecontrol

class Spec {

    def name
    def features = [:]
    def output
    def sourceCode = ''

    private final String shortNameRegex

    Spec(name) {
        this.name = name
        this.shortNameRegex = '.*\\(' + this.name.split('\\.').last() + '\\.groovy\\:([0-9]+)\\)'
    }

    def file(baseFolder) {
        return new File(baseFolder.absolutePath + '/' + name.replaceAll(/\./, '/') + '.groovy')
    }

    def errorLines() {
        List lines = []

        features.each { featureName, feature ->
            if (feature.failure) {
                def match = feature.failure.details =~ shortNameRegex
                match.each { lines.add it[1].toInteger() }
            }
        }

        return lines
    }

    def getFeatureCount() {
        features.size()
    }

    def getFailedFeatureCount() {
        int count = 0
        features.each {featureName, feature ->
            if (feature.failed) {
                count += 1
            }
        }
        return count
    }

    def getSkippedFeatureCount() {
        int count = 0
        features.each {featureName, feature ->
            if (feature.ignored) {
                count += 1
            }
        }
        return count
    }
}
