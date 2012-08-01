package com.github.damagecontrol.htmlreport

import static org.apache.commons.io.FileUtils.writeStringToFile

class HtmlFileWriter {

    def outputFolder

    def write(String fileName, String contents) {
        writeStringToFile(new File(outputFolder.absolutePath + '/' + fileName + '.html'), contents)
    }
}
