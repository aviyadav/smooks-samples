package example;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

public class JavabeanTest2 {

    @Test
    public void test() {
        try {
            Map order = Main2.runSmooks();

            assertNotNull(order);
            assertNotNull(order.get("header"));
            assertNotNull(order.get("orderItems"));
            assertEquals(4, ((List) order.get("orderItems")).size());

            assertEquals(1475433928000L, ((Date) ((Map) order.get("header")).get("date")).getTime());
            assertEquals("Doe", ((Map) order.get("header")).get("customerName"));
            assertEquals(new Long(34456722), ((Map) order.get("header")).get("customerNumber"));

            Map orderItem = (Map) ((List) order.get("orderItems")).get(0);
            assertEquals(8.90d, orderItem.get("price"));
            assertEquals(111L, orderItem.get("productId"));
            assertEquals(new Integer(2), orderItem.get("quantity"));

            orderItem = (Map) ((List) order.get("orderItems")).get(1);
            assertEquals(5.20d, orderItem.get("price"));
            assertEquals(222L, orderItem.get("productId"));
            assertEquals(new Integer(7), orderItem.get("quantity"));
        } catch (IOException | SAXException ex) {
            Logger.getLogger(JavabeanTest2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
