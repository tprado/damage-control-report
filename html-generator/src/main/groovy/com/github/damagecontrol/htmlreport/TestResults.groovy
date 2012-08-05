package com.github.damagecontrol.htmlreport

class TestResults {

    final specs = [:]

    def getSpecList() {
        specs.values().toList()
    }

    def spec(specName) {
        specs[specName] = specs[specName] ?: new Spec(name: specName)
    }
}
