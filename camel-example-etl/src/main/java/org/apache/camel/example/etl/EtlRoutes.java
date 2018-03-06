package org.apache.camel.example.etl;

import org.apache.camel.Exchange;
import static org.apache.camel.language.juel.JuelExpression.el;
import org.apache.camel.spring.SpringRouteBuilder;

public class EtlRoutes extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:src/data?noop=true")
                .convertBodyTo(PersonDocument.class)
                .to("jpa:org.apache.camel.example.etl.CustomerEntity");
        
        from("jpa:org.apache.camel.example.etl.CustomerEntity?consumer.initialDelay=3000&delay=3000&consumeDelete=false&consumeLockEntity=false")
                .setHeader(Exchange.FILE_NAME, el("${in.body.userName}.xml"))
                .to("file:target/customers");
    }
}
