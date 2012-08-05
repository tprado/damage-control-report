package com.github.damagecontrol.report.htmlgenerator

import static org.apache.commons.io.FileUtils.writeStringToFile

class HtmlFileWriter {

    def outputFolder

    def write(String fileName, String contents) {
        writeStringToFile(new File(outputFolder.absolutePath + '/' + fileName + '.html'), contents)
    }
}
