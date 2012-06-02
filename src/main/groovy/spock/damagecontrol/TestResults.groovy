package spock.damagecontrol

class TestResults {

    final specs = [:]

    def getSpecList() {
        specs.values().toList()
    }

    def addFeature(specName, featureName, output) {

        if (!specs[specName]) {
            Spec newSpec = new Spec(specName)
            newSpec.output = output
            specs[specName] = newSpec
        }

        specs[specName].features[featureName] = specs[specName].features[featureName] ?: new Feature()

        specs[specName].features[featureName]
    }
}
