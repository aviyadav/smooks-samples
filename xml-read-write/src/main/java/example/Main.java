package example;

import example.model.Order;
import example.model.OrderItem;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milyn.io.StreamUtils;
import org.milyn.javabean.binding.xml.XMLBinding;
import org.milyn.payload.StringSource;
import org.xml.sax.SAXException;

public class Main {

    public static String orderXMLMessage = readInputMessage();

    public static void main(String[] args) throws IOException, SAXException {
        XMLBinding xmlBinding = new XMLBinding().add("smooks-config.xml");
        xmlBinding.intiailize();
        
        Order order = xmlBinding.fromXML(new StringSource(orderXMLMessage), Order.class);

        String outXML = xmlBinding.toXML(order);
        
        displayInputMessage(orderXMLMessage);
        displayReadJavaObjects(order);
        displayWriteXML(outXML);
    }

    private static void displayInputMessage(String orderXMLMessage) {
        userMessage("\n\n** Press enter to see the Order input sample message (input-message.xml):");
        System.out.println("\n\n");
        System.out.println("==============Source Order XML Message==============");
        System.out.println(orderXMLMessage);
        System.out.println("====================================================");
    }

    private static void displayReadJavaObjects(Order order) {
        userMessage("\n\n** Press enter to see the Order message as bound into the Order object model (XML reading):");
        System.out.println("============Order Java Bean===========");
        System.out.println("Header - Customer Name: " + order.getHeader().getCustomerName());
        System.out.println("       - Customer Num:  " + order.getHeader().getCustomerNumber());
        System.out.println("       - Order Date:    " + order.getHeader().getDate());
        System.out.println("\n");
        System.out.println("Order Items:");
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            OrderItem orderItem = order.getOrderItems().get(i);
            System.out.println("       (" + (i + 1) + ") Product ID:  " + orderItem.getProductId());
            System.out.println("       (" + (i + 1) + ") Quantity:    " + orderItem.getQuantity());
            System.out.println("       (" + (i + 1) + ") Price:       " + orderItem.getPrice());
        }
        System.out.println("======================================");
    }

    private static void displayWriteXML(String xml) {
        userMessage("\n\n** Press enter to see the Order object model serialized back out to XML  (XML writing):");
        System.out.println("==============Serialized Order Bean XML==============");
        System.out.println(xml);
        System.out.println("=====================================================\n");
    }

    private static String readInputMessage() {
        try {
            return StreamUtils.readStreamAsString(new FileInputStream("input-message.xml"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "<no-message/>";
    }

    private static void userMessage(String message) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> " + message);
            in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("\n");
    }
}
