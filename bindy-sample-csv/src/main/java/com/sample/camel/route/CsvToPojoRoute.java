package com.sample.camel.route;

import java.util.List;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;

public class CsvToPojoRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        DataFormat bindy = new BindyCsvDataFormat(Employee.class);
        
        from("direct:start")
                .unmarshal(bindy)
                .process(new Processor() {
            @Override
            public void process(Exchange exchng) throws Exception {
                Message in = exchng.getIn();
                List<Map<String, Object>> modelMap = (List<Map<String, Object>>) in.getBody();
                
                Employee emp = (Employee) modelMap.get(0).get(Employee.class.getCanonicalName());
                System.out.println("Employee Name: " + emp.getFirstName() + " " + emp.getLastName());
            }
        }).end();
    }
}
