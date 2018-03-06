package example;

import java.io.File;
import org.apache.camel.builder.RouteBuilder;

public class ExampleRouteBuilder extends RouteBuilder {

    public ExampleRouteBuilder() {
    }
    
    @Override
    public void configure() throws Exception {
        from("file://" + getWorkingDir() + "?fileName=input-message.xml&noop=true")
                .routeId("inputFileRoute")
                .log("Read file [${file:name}]")
                .to("smooks://smooks-config.xml");
    }

    private File getWorkingDir() {
        String userDir = System.getProperty("user.dir");
        File workingDir = new File(userDir);
        return workingDir;
    }
}
