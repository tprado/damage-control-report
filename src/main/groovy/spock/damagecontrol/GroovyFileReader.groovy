package spock.damagecontrol

import static org.apache.commons.io.FileUtils.readFileToString

class GroovyFileReader {

    static final String FILE_SEPARATOR = '/'

    def inputFolder

    def read(fullQualifiedClassName) {
        String relativeFilePath = fullQualifiedClassName.replaceAll(/\./, FILE_SEPARATOR) + '.groovy'

        readFileToString(new File(inputFolder.absolutePath + FILE_SEPARATOR + relativeFilePath))
    }
}
