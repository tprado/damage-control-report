package com.github.damagecontrol.htmlreport

import static org.apache.commons.io.FileUtils.readFileToString

class HtmlFileWriterTest extends BaseFileHandlingSpec {

    def 'should write contents to file'() {
        when:
        new HtmlFileWriter(outputFolder: testFolder).write('index', 'some content')

        then:
        readFileToString(new File(testFolder.absolutePath + '/index.html')) == 'some content'
    }
}
