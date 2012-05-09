package samples

import spock.lang.Specification

class SampleSpecDefinitionTest extends Specification {

    def 'should do something'() {
        given:
        println 'I did something'

        when:
        println 'I do something'

        then:
        'something should happen'
    }
}
