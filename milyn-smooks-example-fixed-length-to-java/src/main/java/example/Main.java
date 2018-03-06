package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.io.StreamUtils;
import org.milyn.payload.JavaResult;
import org.milyn.payload.StringSource;
import org.xml.sax.SAXException;

public class Main {
    
    private static String messageIn = readInputMessage();
            
    protected static List<Customer> runSmooksTransform() throws SAXException, IOException {
        
        Smooks smooks = new Smooks("smooks-config.xml");
        try {
            
            ExecutionContext executionContext = smooks.createExecutionContext();
            JavaResult result = new JavaResult();
            
            executionContext.setEventListener(new HtmlReportGenerator("target/report/report.html"));
            smooks.filterSource(executionContext, new StringSource(messageIn), result);
            
            return (List<Customer>) result.getBean("customerList");
        } finally {
            smooks.close();
        }
    }
    
    public static void main(String[] args) throws SAXException, IOException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(messageIn);
        System.out.println("======================================\n");
        
        List<Customer> messageOut = Main.runSmooksTransform();
        
        System.out.println("==============Message Out=============");
        System.out.println(messageOut);
        System.out.println("======================================\n\n");
    }
    
    private static String readInputMessage() {
        try {
            return StreamUtils.readStreamAsString(new FileInputStream("input-message.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "<no-message/>";
    }
}
