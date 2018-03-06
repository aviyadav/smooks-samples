package sample.camel;

import java.util.concurrent.TimeUnit;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleCamelApplication.class)
public class SampleCamelApplicationTest {
    
    @Autowired
    CamelContext context;

    @Test
    public void shouldProduceMessages() {
        NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create();
        assertTrue(notify.matches(10, TimeUnit.SECONDS));
    }
}