package com.sample.camel;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext springContext = new ClassPathXmlApplicationContext("camel-spring-context.xml");
        SpringCamelContext camelContext = springContext.getBean("camelcontextbean", SpringCamelContext.class);
        
        try {
            camelContext.start();
            camelContext.addRoutes(springContext.getBean("csvtopojoroute", RouteBuilder.class));
            ProducerTemplate template = camelContext.createProducerTemplate();
            template.sendBody("direct:start", "john, doe, d1, true, city1, state1, 1234");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
