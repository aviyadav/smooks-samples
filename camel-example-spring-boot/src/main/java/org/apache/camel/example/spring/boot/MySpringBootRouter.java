package org.apache.camel.example.spring.boot;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MySpringBootRouter extends RouteBuilder {
    
    @Autowired
    private HealthEndpoint health;
    
    @Bean
    String myBean() {
        return "I'm Spring bean!";
    }

    @Override
    public void configure() throws Exception {
        from("timer:trigger")
            .transform()
            .simple("ref:myBean")
            .to("log:out");
        
        from("timer:status")
            .bean(health, "invoke")
            .log("Health is {body}");
    }
}
