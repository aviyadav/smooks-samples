package org.apache.camel.example.spring.boot;

import java.util.concurrent.TimeUnit;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MySpringBootRouter.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class MySpringBootRouterTest extends Assert {
    
    @Autowired
    CamelContext context;

    @Test
    public void shouldProduceMessages() {
        NotifyBuilder notify = new NotifyBuilder(context).whenDone(4).create();
        assertTrue(notify.matches(10, TimeUnit.SECONDS));
    }
}