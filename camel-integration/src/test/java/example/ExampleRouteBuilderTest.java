package example;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExampleRouteBuilderTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new ExampleRouteBuilder();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.addComponent("jms", context.getComponent("mock"));
        return context;
    }
    
    @Test
    public void route() throws Exception {
        MockEndpoint irelandMockQueue = getMockEndpoint("jms:queue:ireland");
        irelandMockQueue.setExpectedMessageCount(1);
        
        Thread.sleep(1000);
        irelandMockQueue.assertIsSatisfied(1000);
    }
}