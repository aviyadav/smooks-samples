package example;

import example.model.Order;
import example.model.OrderItem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

public class JavabeanTest {

    @Test
    public void test() {
        try {
            Order order = Main.runSmooks();
            
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
        } catch (IOException | SAXException ex) {
            Logger.getLogger(JavabeanTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
