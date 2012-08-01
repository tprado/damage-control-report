package com.github.damagecontrol.htmlreport

class LineNumberAnnotatorTest extends BaseSpec {

    def annotator = new LineNumberAnnotator()

    def 'should annotate each line with its number'() {
        given:
        String source = '''class SampleTest extends Spec {
    def 'feature name'() {
        given:
        // some code
    }
}
'''

        when:
        String annotatedSource = annotator.annotate(source)

        then:
        annotatedSource == '''#1#class SampleTest extends Spec {
#2#    def 'feature name'() {
#3#        given:
#4#        // some code
#5#    }
#6#}
'''
    }

    def 'should annotate single line file'() {
        given:
        String source = 'class SampleTest extends Spec {}'

        when:
        String annotatedSource = annotator.annotate(source)

        then:
        annotatedSource == '#1#class SampleTest extends Spec {}'
    }
}
