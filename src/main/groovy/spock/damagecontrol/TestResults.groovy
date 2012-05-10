package spock.damagecontrol

class TestResults {

    def specs = [:]

    def addFeature(specName, featureName) {

        if (!specs[specName]) {
            Spec newSpec = new Spec(specName)
            specs[specName] = newSpec
        }

        if (!specs[specName].features[featureName]) {
            specs[specName].features[featureName] = new Feature()
        }

        return specs[specName].features[featureName]
    }
}
