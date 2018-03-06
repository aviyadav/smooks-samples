package example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.csv.CSVRecordParserConfigurator;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.flatfile.Binding;
import org.milyn.flatfile.BindingType;
import org.milyn.io.StreamUtils;
import org.milyn.payload.JavaResult;
import org.milyn.payload.StringSource;
import org.xml.sax.SAXException;

public class Main {

    private static String messageIn = readInputMessage();

    public static void main(String[] args) throws IOException, SAXException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(messageIn);
        System.out.println("======================================\n");
        
        List<Customer> messageOut = Main.runSmooksTransform();

        System.out.println("==============Message Out=============");
//        System.out.println(messageOut);
        for (Customer customer : messageOut) {
            System.out.println(customer.toString());
        }
        System.out.println("======================================\n\n");
        
    }

    protected static List<Customer> runSmooksTransform() throws IOException, SAXException {

        Smooks smooks = new Smooks();

        try {

            smooks.setReaderConfig(new CSVRecordParserConfigurator("firstName,lastName,gender,age,country").setBinding(new Binding("customerList", Customer.class, BindingType.LIST)));
            
            ExecutionContext context = smooks.createExecutionContext();

            JavaResult result = new JavaResult();
            context.setEventListener(new HtmlReportGenerator("target/report/report.html"));

            smooks.filterSource(context, new StringSource(messageIn), result);

            return (List<Customer>) result.getBean("customerList");
        } finally {
            smooks.close();
        }
    }

    private static String readInputMessage() {
        try {
            return StreamUtils.readStreamAsString(new FileInputStream("input-message.csv"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "<no-message />";
    }
}
