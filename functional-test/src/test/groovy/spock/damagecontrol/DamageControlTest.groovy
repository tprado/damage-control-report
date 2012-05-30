package spock.damagecontrol

import spock.lang.Specification
import spock.lang.Ignore

class DamageControlTest extends Specification {

    private static final File SAMPLE_MVN_PROJECT = new File("functional-test/sample-spock-project")

    @Ignore
    def "should generate reports using Spock specifications"() {
        when: "I run my Maven build"
        def mvn = "mvn clean test damage-control:report".execute(["JAVA_HOME=${System.getenv("JAVA_HOME")}"], SAMPLE_MVN_PROJECT)
        mvn.waitFor()

        then: "I should see a nice report"
        assert mvn.exitValue() == 0
        assert new File(SAMPLE_MVN_PROJECT.absolutePath + "/target/damage-control-reports/HelloSpockTest.html").exists()
    }
}
