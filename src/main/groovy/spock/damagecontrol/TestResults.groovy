package spock.damagecontrol

class TestResults {

    final specs = [:]

    def getSpecList() {
        specs.values().toList()
    }

    def addFeature(specName, featureName, output) {
        def spec = specs[specName]

        if (!spec) {
            spec = new Spec(name: specName, output: output)
            specs[specName] = spec
        }

        spec.features[featureName] = spec.features[featureName] ?: new Feature()
    }
}
