package spock.damagecontrol.testresults

class TestResults {

    def specs = [:]

    def addFeature(specName, featureName) {

        if (!specs[specName]) {
            def newSpec = new Spec(specName)
            specs[specName] = newSpec
        }

        if (!specs[specName].features[featureName]) {
            specs[specName].features[featureName] = new Feature()
        }

        return specs[specName].features[featureName]
    }
}
