package spock.damagecontrol

class LineNumberAnnotator {

    def annotate(sourceCode) {
        int lineNumber = 1
        sourceCode.replaceAll(/(?m)^.*$/, { "#${lineNumber++}#${it}" })
    }
}
