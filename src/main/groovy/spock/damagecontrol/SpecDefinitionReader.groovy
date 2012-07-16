package spock.damagecontrol

import static org.apache.commons.io.FileUtils.readFileToString

class SpecDefinitionReader {

    def specsFolder

    def read(spec) {
        spec.parseFeatureDefinition(readFileToString(spec.file(specsFolder)))
    }
}
