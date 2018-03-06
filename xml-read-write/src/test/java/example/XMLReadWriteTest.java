package example;

import org.junit.Test;
import static org.junit.Assert.*;
import example.model.Order;
import example.model.OrderItem;

import java.io.IOException;
import java.io.StringWriter;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.milyn.javabean.binding.xml.XMLBinding;
import org.milyn.payload.StringSource;
import org.xml.sax.SAXException;

public class XMLReadWriteTest {

    @Test
    public void test() throws IOException, SAXException {
        XMLBinding xmlBinding = new XMLBinding().add("smooks-config.xml");
        xmlBinding.intiailize();

        Order order = xmlBinding.fromXML(new StringSource(Main.orderXMLMessage), Order.class);

        assertNotNull(order);
        assertNotNull(order.getHeader());
        assertNotNull(order.getOrderItems());
        assertEquals(2, order.getOrderItems().size());

        assertEquals(1163616328000L, order.getHeader().getDate().getTime());
        assertEquals("Joe", order.getHeader().getCustomerName());
        assertEquals(new Long(123123), order.getHeader().getCustomerNumber());

        OrderItem orderItem = order.getOrderItems().get(0);
        assertEquals(8.90d, orderItem.getPrice(), 0d);
        assertEquals(111, orderItem.getProductId());
        assertEquals(new Integer(2), orderItem.getQuantity());

        orderItem = order.getOrderItems().get(1);
        assertEquals(5.20d, orderItem.getPrice(), 0d);
        assertEquals(222, orderItem.getProductId());
        assertEquals(new Integer(7), orderItem.getQuantity());

        StringWriter orderWriter = new StringWriter();
        xmlBinding.toXML(order, orderWriter);

        XMLUnit.setIgnoreWhitespace(true);
        XMLAssert.assertXMLEqual(Main.orderXMLMessage, orderWriter.toString());
    }
}
