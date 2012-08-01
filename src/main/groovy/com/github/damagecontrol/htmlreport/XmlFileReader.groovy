package com.github.damagecontrol.htmlreport

import static org.apache.commons.io.FileUtils.iterateFiles
import static org.apache.commons.io.IOUtils.closeQuietly

class XmlFileReader {

    private static final boolean INCLUDE_SUB_FOLDERS = true
    private static final String[] XML = ['xml']

    def inputFolder

    @SuppressWarnings('CatchException')
    @SuppressWarnings('Println')
    def forEach(closure) {
        iterateFiles(inputFolder, XML, INCLUDE_SUB_FOLDERS).each {file ->
            def reader

            try {
                reader = new FileReader(file)
                closure(reader)
            } catch (Exception e) {
                println "Error reading file '${file}': ${e.message}"
            } finally {
                closeQuietly(reader)
            }
        }
    }
}
