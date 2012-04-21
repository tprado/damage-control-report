package spock.damagecontrol.plugins.maven;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static org.apache.commons.io.FileUtils.iterateFiles;

/**
 * @goal report
 */
public class DamageControlMojo extends AbstractMojo {

    /**
     * @parameter default-value="src/test/groovy"
     */
    private File specsSourceFolder;

    /**
     * @parameter default-value="target/damage-control-reports"
     */
    private File reportsTargetFolder;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Damage Control, report:");
        getLog().info("specs source folder=" + specsSourceFolder);
        getLog().info("reports target folder=" + reportsTargetFolder);


        Iterator iterator = iterateFiles(specsSourceFolder, new String[]{"groovy"}, true);
        while (iterator.hasNext()) {
            File srcFile = (File) iterator.next();
            File destFile = new File(reportsTargetFolder.getAbsoluteFile() + "/" + srcFile.getName().replaceAll("\\.groovy", ".html"));

            try {
                FileUtils.copyFile(srcFile, destFile);
            } catch (IOException e) {
                new MojoExecutionException("error copying files", e);
            }
        }
    }
}
