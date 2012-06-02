package spock.damagecontrol

import static org.apache.commons.io.FileUtils.readFileToString

class SpecDefinitionReader {

    def final specsFolder

    SpecDefinitionReader(folder) {
        specsFolder = folder
    }

    def read(spec) {
        spec.sourceCode = readFileToString(spec.file(specsFolder))
    }
}
