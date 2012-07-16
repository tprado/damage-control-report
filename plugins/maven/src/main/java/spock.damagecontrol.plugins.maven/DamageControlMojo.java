package spock.damagecontrol.plugins.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import spock.damagecontrol.Report;

import java.io.File;

/**
 * @goal report
 */
public class DamageControlMojo extends AbstractMojo {

    /**
     * @parameter default-value="target/surefire-reports"
     */
    private File testResultsFolder;

    /**
     * @parameter default-value="src/test/groovy"
     */
    private File specDefinitionsFolder;

    /**
     * @parameter default-value="target/damage-control-reports"
     */
    private File outputFolder;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Damage Control, report:");
        getLog().info("test results folder=" + testResultsFolder);
        getLog().info("specs definition folder=" + specDefinitionsFolder);
        getLog().info("reports target folder=" + outputFolder);

        Report report = new Report();
        report.setTestResultsFolder(testResultsFolder);
        report.setSpecDefinitionsFolder(specDefinitionsFolder);
        report.setOutputFolder(outputFolder);
        report.run();
    }
}
