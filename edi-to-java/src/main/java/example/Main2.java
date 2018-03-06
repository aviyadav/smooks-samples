package example;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.stream.StreamSource;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.io.StreamUtils;
import org.milyn.payload.JavaResult;
import org.milyn.payload.StringResult;
import org.xml.sax.SAXException;

public class Main2 {

    private static byte[] messageIn = readInputMessage();

    public static void main(String[] args) throws IOException, SAXException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(messageIn);
        System.out.println("======================================\n");

        pause("The EDI input stream can be seen above.  Press 'enter' to see this stream transformed into XML...");
        
        String messageOut = Main2.runSmooksTransform();
        
        System.out.println("==============Message Out=============");
        System.out.println(messageOut);
        System.out.println("======================================\n\n");

        pause("And that's it!  Press 'enter' to finish...");
    }

    private static void pause(String message) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> " + message);
            in.readLine();
        } catch (IOException e) {
        }
        System.out.println("\n");
    }

    private static byte[] readInputMessage() {
        try {
            return StreamUtils.readStream(new FileInputStream("input-message.edi"));
        } catch (IOException ex) {
            Logger.getLogger(Main2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "<no-message/>".getBytes();
    }

    public static String runSmooksTransform() throws IOException, SAXException {
        Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(new Locale("en", "IE"));
        
        Smooks smooks = new Smooks("smooks-config-2.xml");
        
        try {
            ExecutionContext executionContext = smooks.createExecutionContext();
            StringResult result = new StringResult();
            executionContext.setEventListener(new HtmlReportGenerator("target/report/report.html"));
            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), result);
            Locale.setDefault(defaultLocale);
            
            return result.getResult();
        } finally {
            smooks.close();
        }
    }
}
