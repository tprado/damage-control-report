package com.github.damagecontrol.htmlreport

class LineNumberAnnotator {

    def annotate(sourceCode) {
        int lineNumber = 1
        sourceCode.replaceAll(/(?m)^.*$/, { "#${lineNumber++}#${it}" })
    }
}
