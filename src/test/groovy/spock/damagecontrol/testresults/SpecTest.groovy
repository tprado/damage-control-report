package spock.damagecontrol.testresults

import spock.lang.Specification

class SpecTest extends Specification {

    def 'should compose spec file name based on the spec name'() {
        given:
        File baseFolder = new File('src/test/resources')
        Spec spec = new Spec('samples.SampleSpecificationTest')

        when:
        File specFile = spec.file(baseFolder)

        then:
        specFile.absolutePath.contains('src/test/resources/samples/SampleSpecificationTest.groovy')
    }
}
