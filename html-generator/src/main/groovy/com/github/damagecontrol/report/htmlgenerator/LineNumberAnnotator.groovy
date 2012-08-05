package com.github.damagecontrol.report.htmlgenerator

class LineNumberAnnotator {

    def annotate(sourceCode) {
        int lineNumber = 1
        sourceCode.replaceAll(/(?m)^.*$/, { "#${lineNumber++}#${it}" })
    }
}
