package example;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.io.StreamUtils;
import org.xml.sax.SAXException;

public class Main {

    private static byte[] messageIn = readInputMessage();
    
    public static void main(String[] args) throws Exception {
        System.out.println("\n\n==============Message In==============");
        System.out.println(new String(messageIn));
        System.out.println("======================================\n");
        
        String messageOut = Main.runSmooksTransform();
        
        System.out.println("==============Message Out=============");
        System.out.println(messageOut);
        System.out.println("======================================\n\n");
    }

    private static byte[] readInputMessage() {
        try {
            return StreamUtils.readStream(new FileInputStream("input-message.csv"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "inpit file not found.", ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Other IO Exception", ex);
        }
        
        return "<no-message/>".getBytes();
    }
    
    protected static String runSmooksTransform() throws IOException, SAXException {
        Smooks smooks = new Smooks("smooks-config.xml");
        
        try {
            ExecutionContext context = smooks.createExecutionContext();
            StringWriter writer = new StringWriter();
            
            context.setEventListener(new HtmlReportGenerator("target/report/report.html"));
            smooks.filterSource(context, new StreamSource(new InputStreamReader(new ByteArrayInputStream(messageIn), "UTF-8")), new StreamResult(writer));
            
            return writer.toString();
        } finally {
            smooks.close();
        }
    }
}
