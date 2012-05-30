import spock.lang.Specification

class HiSpockTest extends Specification {

    def "length of Spock's and his friends' names"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 4
        "Kirk"   | 4
        "Scotty" | 6
    }
}
