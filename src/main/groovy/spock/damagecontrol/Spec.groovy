package spock.damagecontrol

class Spec {

    static final String FILE_SEPARATOR = '/'

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

    int getSuccessPercentage() {
        def successfulFeatureCount = featureCount - failedFeatureCount
        float successPercentage = successfulFeatureCount/featureCount * 100
        successPercentage.round(0)
    }

    def parseFeatureDefinition(sourceCode) {
        features.each {featureName, feature ->

            def match = sourceCode =~ "(?s)def\\s?('|\")${featureName}('|\")\\s*\\(\\s*\\)\\s*\\{(.*)"

            StringBuilder featureSourceCode = new StringBuilder()
            String partialSourceCode = match[0][3]
            int curlyBracketsToClose = 1

            for (int i = 0; i < partialSourceCode.length() && curlyBracketsToClose > 0; i++) {
                char c = partialSourceCode.charAt(i)
                if (c == '}') {
                    curlyBracketsToClose--
                } else if (c == '{') {
                    curlyBracketsToClose++
                } else {
                    featureSourceCode.append(c)
                }
            }

            feature.sourceCode = featureSourceCode.toString()
        }
    }
}
