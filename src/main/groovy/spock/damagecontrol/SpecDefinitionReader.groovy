package spock.damagecontrol

import static org.apache.commons.io.FileUtils.readFileToString

class SpecDefinitionReader {

    private final File specsFolder

    SpecDefinitionReader(folder) {
        specsFolder = folder
    }

    def read(spec) {
        String contents = readFileToString(spec.file(specsFolder))
        return new SpecDefinition(contents)
    }
}
