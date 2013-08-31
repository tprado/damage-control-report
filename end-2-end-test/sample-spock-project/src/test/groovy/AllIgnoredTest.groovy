import spock.lang.Specification
import spock.lang.Ignore

@Ignore
class AllIgnoredTest extends Specification {

    def "length of Spock's and his friends names"() {
        given: "Spock have 3 friends"
        def spockFriends = ["Kirk", "Sulu","McCoy"]

        when: "He meets another friend"
        spockFriends.add("Scotty")

        then: "Spock now have 4 friends"
        spockFriends.size()==4

    }
}
