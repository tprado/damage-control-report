package spock.damagecontrol

import spock.lang.Specification

class DamageControlTest extends Specification {

    def "should generate reports using Spock specifications"() {
        given: "I have a Spock spec"

        when: "I run my Maven build"

        then: "I should see a nice report"
        false
    }
}
