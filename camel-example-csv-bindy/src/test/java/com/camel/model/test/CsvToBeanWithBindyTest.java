package com.camel.model.test;

import com.camel.model.CSVBean;
import java.util.List;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import static org.junit.Assert.*;

public class CsvToBeanWithBindyTest extends CamelTestSupport {

    @Test
    public void testCSV() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:queue.csv");
        mock.expectedMessageCount(1);
        assertMockEndpointsSatisfied();

        List csv = (List) mock.getReceivedExchanges().get(0).getIn().getBody();
        CSVBean csv1 = (CSVBean) csv.get(0);
        assertEquals("row 01", csv1.getFirst());

        CSVBean csv2 = (CSVBean) csv.get(1);
        assertEquals("row 11", csv2.getFirst());
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                context.setTracing(true);

                from("file://src/test/resources?noop=true&fileName=test2.csv")
                .unmarshal(new BindyCsvDataFormat(CSVBean.class))
                .to("mock:queue.csv");
            }
        };
    }
}
