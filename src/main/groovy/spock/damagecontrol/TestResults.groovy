package spock.damagecontrol

class TestResults {

    def specs = [:]

    def addFeature(specName, featureName, output) {

        if (!specs[specName]) {
            Spec newSpec = new Spec(specName)
            newSpec.output = output
            specs[specName] = newSpec
        }

        if (!specs[specName].features[featureName]) {
            specs[specName].features[featureName] = new Feature()
        }

        return specs[specName].features[featureName]
    }
}
