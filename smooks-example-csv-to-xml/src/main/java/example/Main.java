package example;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
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

//    private static byte[] messageIn = readInputMessage("input-message.csv");
    private static byte[] messageIn = readInputMessage("input-message-2.csv");

    public static void main(String[] args) throws IOException, SAXException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(new String(messageIn));
        System.out.println("======================================\n");

//        String messageOut = Main.runSmooksTransform("smooks-config.xml");
        String messageOut = Main.runSmooksTransform("smooks-config-2.xml");

        System.out.println("==============Message Out=============");
        System.out.println(messageOut);
        System.out.println("======================================\n\n");
    }

    protected static String runSmooksTransform(String configFileName) throws IOException, SAXException {
        Smooks smooks = new Smooks(configFileName);

        try {
            ExecutionContext executionContext = smooks.createExecutionContext();
            StringWriter writer = new StringWriter();

            executionContext.setEventListener(new HtmlReportGenerator("target/report/report.html"));
            smooks.filterSource(executionContext, new StreamSource(new InputStreamReader(new ByteArrayInputStream(messageIn), "UTF-8")), new StreamResult(writer));

            return writer.toString();

        } finally {
            smooks.close();
        }

    }

    private static byte[] readInputMessage(String csvFileName) {
        try {
            return StreamUtils.readStream(new FileInputStream(csvFileName));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "<no-message/>".getBytes();
    }
}
