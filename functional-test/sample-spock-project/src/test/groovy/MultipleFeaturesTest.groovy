import spock.lang.Ignore
import spock.lang.Specification

class MultipleFeaturesTest extends Specification {

    def "length of Spock's and his friends' names"() {
        expect: "name lengh is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }

    def "length of Spock's and his friends' names FAILED"() {
        expect: "name lengh is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 6
        "Kirk"   | 4
        "Scotty" | 6
    }

    @Ignore
    def "length of Spock's and his friends' names SKIPPED"() {
        expect: "name lengh is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 6
        "Kirk"   | 4
        "Scotty" | 6
    }

}
