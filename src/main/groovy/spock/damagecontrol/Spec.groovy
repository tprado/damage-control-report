package spock.damagecontrol

class Spec {

    static final String FILE_SEPARATOR = System.getProperty('file.separator')

    def name
    def features = [:]
    def output
    def sourceCode = ''
    def duration

    final shortNameRegex

    Spec(name) {
        this.name = name
        this.shortNameRegex = '.*\\(' + this.name.split('\\.').last() + '\\.groovy\\:([0-9]+)\\)'
    }

    def file(baseFolder) {
        new File(baseFolder.absolutePath + FILE_SEPARATOR + name.replaceAll(/\./, FILE_SEPARATOR) + '.groovy')
    }

    def errorLines() {
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
}
