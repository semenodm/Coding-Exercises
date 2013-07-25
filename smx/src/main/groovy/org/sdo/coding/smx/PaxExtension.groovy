package org.sdo.coding.smx

import org.ops4j.pax.exam.CoreOptions
import org.ops4j.pax.exam.ExamSystem
import org.ops4j.pax.exam.TestContainer
import org.ops4j.pax.exam.karaf.options.KarafDistributionOption
import org.ops4j.pax.exam.karaf.options.LogLevelOption
import org.ops4j.pax.exam.karaf.options.configs.CustomProperties
import org.ops4j.pax.exam.options.MavenArtifactUrlReference
import org.ops4j.pax.exam.spi.PaxExamRuntime
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.SpecInfo

import static org.ops4j.pax.exam.CoreOptions.maven
import static org.ops4j.pax.exam.CoreOptions.systemProperty
import static org.ops4j.pax.exam.CoreOptions.when
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel
import static org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel.WARN
import static org.ops4j.pax.exam.karaf.options.configs.CustomProperties.KARAF_FRAMEWORK

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 7/25/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
class PaxExtension extends AbstractAnnotationDrivenExtension<RunPax> {
    @Override
    void visitSpecAnnotation(RunPax annotation, SpecInfo sp) {
        sp.addListener(new AbstractRunListener() {
            private TestContainer container

            @Override
            void beforeSpec(SpecInfo spec) {
                ExamSystem system = PaxExamRuntime.createServerSystem(
                        CoreOptions.options(
                                karafDistributionConfiguration("mvn:org.apache.servicemix/apache-servicemix/4.4.1-fuse-03-06/tar.gz", // artifact to unpack and use
                                        "servicemix", // name; display only
                                        "2.2.4")
                        .unpackDirectory(new File("target/paxexam/unpack/"))
                        .useDeployFolder(false),
//				debugConfiguration("5005", true),
                        configureConsole().ignoreLocalConsole(),
                        when(isEquinox()).useOptions(
                                editConfigurationFilePut(
                                        KARAF_FRAMEWORK, "equinox"),
                                systemProperty("pax.exam.framework").value(
                                        System.getProperty("pax.exam.framework")),
                                systemProperty("osgi.console").value("6666"),
                                systemProperty("osgi.console.enable.builtin").value("true")
                        ),
                        logLevel(WARN),
                        keepRuntimeFolder()))
                container = PaxExamRuntime.createContainer(system)
                container.start()
                Thread.sleep(10000)
//                def sout = new StringBuffer(), serr = new StringBuffer()
//                def proc = """expect <<EOD
//spawn ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no smx@localhost -p 8101
//expect "password"
//send "smx\r"
//expect ">"
//send "list\r"
//expect ">"
//send "logout\r"
//EOD""".execute()
//                proc.consumeProcessOutput(sout, serr)
//                proc.waitForOrKill(1000)
//                println "out> $sout err> $serr"

                def ant = new AntBuilder()
                ant.sshexec(host:"localhost",
                        port:'8101',
                        username:"smx",
                        password:'smx',
                        trust:"yes",
                        command:"list",
                        outputproperty: 'result',
                        knownhosts:'/dev/null')

                def result = ant.project.properties.'result'
                println "result is $result"
                def installed = result =~ /(?m)^(.*Installed.*)$/
                println "installed ${installed[0]}"

            }

            @Override
            void afterSpec(SpecInfo spec) {
                container?.stop()
            }
        })
    }

    private MavenArtifactUrlReference mvnKarafDist() {
        return maven().groupId("org.apache.servicemix").artifactId("apache-servicemix")
                .type("tar.gz").version(getKarafVersion())
    }

    String getKarafVersion() {
        return '4.4.1-fuse-03-06'
    }
    private boolean isEquinox() {
        return "equinox".equals(System.getProperty("pax.exam.framework"));
    }
}
