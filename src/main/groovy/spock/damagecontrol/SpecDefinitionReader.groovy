package spock.damagecontrol

import static org.apache.commons.io.FileUtils.readFileToString

class SpecDefinitionReader {

    final specsFolder

    SpecDefinitionReader(folder) {
        specsFolder = folder
    }

    def read(spec) {
        spec.sourceCode = readFileToString(spec.file(specsFolder))
        spec.parseFeatureDefinition(readFileToString(spec.file(specsFolder)))
    }
}
