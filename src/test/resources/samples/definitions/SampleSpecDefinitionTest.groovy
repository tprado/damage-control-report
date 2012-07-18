package samples.definitions

import spock.lang.Specification

class SampleSpecDefinitionTest extends Specification {

    def 'should do something'() {
        given: 'I did something'
        println 'I did something'

        when: 'I do something'
        println 'I do something'

        then: 'something should happen'
        'something should happen'
    }
}
