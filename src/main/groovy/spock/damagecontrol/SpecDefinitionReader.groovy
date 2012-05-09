package spock.damagecontrol

import spock.damagecontrol.testresults.Spec

import static org.apache.commons.io.FileUtils.readFileToString

class SpecDefinitionReader {

    final File specsFolder

    SpecDefinitionReader(File folder) {
        specsFolder = folder
    }

    SpecDefinition read(Spec spec) {
        String contents = readFileToString(spec.file(specsFolder))
        return new SpecDefinition(contents)
    }
}
