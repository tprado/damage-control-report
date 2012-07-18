package spock.damagecontrol

class TestResults {

    final specs = [:]

    def getSpecList() {
        specs.values().toList()
    }

    def spec(specName) {
        specs[specName] = specs[specName] ?: new Spec(name: specName)
    }
}
