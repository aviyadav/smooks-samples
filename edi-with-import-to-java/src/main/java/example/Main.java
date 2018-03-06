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
import org.xml.sax.SAXException;

public class Main {

    private static byte[] messageIn = readInputMessage();

    private final Smooks smooks;

    protected Main() throws IOException, SAXException {
        smooks = new Smooks("smooks-config.xml");
    }

    public static void main(String[] args) throws IOException, SAXException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(messageIn);
        System.out.println("======================================\n");
        
        pause("The EDI input stream can be seen above.  Press 'enter' to see how this stream is transformed the Order Object graph...");
        
        Main smooksMain = new Main();
        ExecutionContext executionContext = smooksMain.smooks.createExecutionContext();
        JavaResult result = smooksMain.runSmooksTransform(executionContext);

        System.out.println("\n==============EDI as Java Object Graph=============");
        System.out.println(result.getBean("order"));
        System.out.println("======================================\n\n");
    }

    public JavaResult runSmooksTransform() throws IOException {
        ExecutionContext executionContext = smooks.createExecutionContext();
        return runSmooksTransform(executionContext);
    }

    protected JavaResult runSmooksTransform(ExecutionContext executionContext) throws IOException {
        try {
            Locale defaultLocale = Locale.getDefault();
            Locale.setDefault(new Locale("en", "IE"));

            JavaResult javaResult = new JavaResult();
            executionContext.setEventListener(new HtmlReportGenerator("target/report/report.html"));

            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), javaResult);

            return javaResult;
        } finally {
            smooks.close();
        }
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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "<no-message/>".getBytes();
    }
}
