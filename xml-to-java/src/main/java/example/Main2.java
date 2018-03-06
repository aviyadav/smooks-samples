package example;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.stream.StreamSource;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.io.StreamUtils;
import org.milyn.payload.JavaResult;
import org.xml.sax.SAXException;

public class Main2 {

    private static byte[] messageIn = readInputMessage();

    public static void main(String[] args) throws IOException, SAXException {
        System.out.println("\n\n");
        System.out.println("==============Message In==============");
        System.out.println(new String(messageIn));
        System.out.println("======================================\n");

        Map order = Main2.runSmooks();

        System.out.println("============Order Javabeans===========");
        System.out.println("Header - Customer Name: " + ((Map) order.get("header")).get("customerName"));
        System.out.println("       - Customer Num:  " + ((Map) order.get("header")).get("customerNumber"));
        System.out.println("       - Order Date:    " + ((Map) order.get("header")).get("date"));
        System.out.println("\n");
        System.out.println("Order Items:");
        List<Map> orderItems = (List<Map>) order.get("orderItems");
        for (int i = 0; i < orderItems.size(); i++) {
            Map orderItem = orderItems.get(i);
            System.out.println("       (" + (i + 1) + ") Product ID:  " + orderItem.get("productId"));
            System.out.println("       (" + (i + 1) + ") Quantity:    " + orderItem.get("quantity"));
            System.out.println("       (" + (i + 1) + ") Price:       " + orderItem.get("price"));
        }
        System.out.println("======================================");
        System.out.println("\n\n");
    }

    protected static Map runSmooks() throws IOException, SAXException {
        Smooks smooks = new Smooks("smooks-config-2.xml");
        try {
            ExecutionContext executionContext = smooks.createExecutionContext();
            JavaResult result = new JavaResult();

            executionContext.setEventListener(new HtmlReportGenerator("target/report/report.html"));
            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), result);

            return (Map) result.getBean("order");
        } finally {
            smooks.close();
        }
    }

    private static byte[] readInputMessage() {
        try {
            return StreamUtils.readStream(new FileInputStream("input-message-2.xml"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "<no-message/>".getBytes();
    }
}
