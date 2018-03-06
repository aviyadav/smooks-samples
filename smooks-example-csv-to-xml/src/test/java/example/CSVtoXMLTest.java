package example;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.milyn.io.StreamUtils;
import org.xml.sax.SAXException;

public class CSVtoXMLTest {
    
    @Test
    public void test() {
        try {
            
            byte[] expected = StreamUtils.readStream(new FileInputStream("expected-2.xml"));
            String result = Main.runSmooksTransform("smooks-config-2.xml");
            
            StringBuffer s1 = StreamUtils.trimLines(new ByteArrayInputStream(expected));
            StringBuffer s2 = StreamUtils.trimLines(new ByteArrayInputStream(result.getBytes()));
            
//            System.out.println("S1 - " + s1);
//            System.out.println("S2 - " + s2);
            
            assertEquals("Expected:\n" + s1 + "\nActual:\n" + s2, s1.toString(), s2.toString());
        } catch (IOException | SAXException ex) {
            Logger.getLogger(CSVtoXMLTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
