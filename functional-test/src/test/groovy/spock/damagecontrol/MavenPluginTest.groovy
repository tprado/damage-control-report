package spock.damagecontrol

import spock.lang.Specification

class MavenPluginTest extends Specification {

    private static final String JAVA_HOME = System.getenv("JAVA_HOME")
    private static final String MVN = System.getenv("M2_HOME") + '/bin/mvn'

    private static final File SAMPLE_MVN_PROJECT = new File("sample-spock-project")


    def "should generate reports using Spock specifications"() {
        when: "I run a Maven build"
        def mvn = "$MVN clean test".execute(["JAVA_HOME=${JAVA_HOME}"], SAMPLE_MVN_PROJECT)
        mvn.waitFor()

        then: "the build finishes successfuly"
        assert mvn.exitValue() == 0

        and: "I see a nice HTML report"
        assert new File(SAMPLE_MVN_PROJECT.absolutePath + "/target/damage-control-reports/MultipleFeaturesTest.html").exists()
    }
}
