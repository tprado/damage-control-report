package spock.damagecontrol

class FeatureTest extends BaseSpec {

    def feature

    def setup(){
        given:
        feature = new Feature()
    }

    def 'should fail the feature'() {
        when:
        feature.fail 'error', 'error details'

        then:
        feature.failed
    }

    def 'should ignore the feature'() {
        when:
        feature.ignore()

        then:
        feature.ignored
    }

    def 'should not be ignored by default'() {
        expect:
        !new Feature().ignored
    }

    def 'should indicate the result as "passed" when there is no failure'() {
        expect:
        feature.result == 'passed'
    }

    def 'should indicate the result as "failed" when there is a failure'() {
        feature.fail 'error', 'some error detailed'

        expect:
        feature.result == 'failed'
    }

    def 'should indicate the result as "skipped" when the test is ignored'() {
        feature.ignore()

        expect:
        feature.result == 'skipped'
    }

    def 'should indicate if the feature failed'() {
        feature.fail 'error', 'some error detailed'

        expect:
        feature.failed
    }

    def 'should have empty source code by default'() {
        expect:
        new Feature().sourceCode == ''
    }

    def 'should have a given statement in blocks'() {

        given:
        feature.sourceCode = '''
        given: 'something'
        // some steps
'''

        when:
        feature.readBlocks()

        then:
        feature.blocks.contains("given: 'something'")
    }

    def 'should have a when statement in blocks'() {

        given:
        feature.sourceCode = '''
        when: 'I do something'
        //other steps
'''

        when:
        feature.readBlocks()

        then:
        feature.blocks.contains("when: 'I do something'")
    }

    def 'should have a then statement in blocks'() {

        given:
        feature.sourceCode = '''
        then: 'something happens'
'''

        when:
        feature.readBlocks()

        then:
        feature.blocks.contains("then: 'something happens'")
    }

    def 'should have an and statement in blocks'() {

        given:
        feature.sourceCode = '''
        and: 'something else happens'
'''

        when:
        feature.readBlocks()

        then:
        feature.blocks.contains("and: 'something else happens'")
    }

    def 'should have a expect statement in blocks'() {

        given:
        feature.sourceCode = '''
        expect: ''
        // some steps
'''

        when:
        feature.readBlocks()

        then:
        feature.blocks.contains("expect: ''")
    }

    def 'should have a setup statement in blocks'() {

        given:
        feature.sourceCode = '''
        setup: 'something'
        // some steps
'''

        when:
        feature.readBlocks()

        then:
        feature.blocks.contains("setup: 'something'")
    }

    def 'should have a cleanup statement in blocks'() {

        given:
        feature.sourceCode = '''
        cleanup: 'something'
        // some steps
'''

        when:
        feature.readBlocks()

        then:
        feature.blocks.contains("cleanup: 'something'")
    }

    def 'should have all the blocks in the correct order'() {

        given:
        feature.sourceCode = '''
        given: 'something'
        // some steps
        when: 'I do something'
        //other steps
        and: 'I do something else'
        //other steps
        then: 'something happens'
        and: 'something else happens'
'''

        when:
        feature.readBlocks()

        then:
        def expectedBlocks = ["given: 'something'",
                "when: 'I do something'",
                "and: 'I do something else'",
                "then: 'something happens'",
                "and: 'something else happens'"]
        feature.blocks == expectedBlocks

    }
}
