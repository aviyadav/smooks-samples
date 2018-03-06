package example;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.milyn.io.StreamUtils;
import org.xml.sax.SAXException;

public class CSVVariableRecordTest {
    
    @Test
    public void test() throws IOException, SAXException {
        byte[] expected = StreamUtils.readStream(new FileInputStream("expected.xml"));
        String result = Main.runSmooksTransform();
        
        StringBuffer s1 = StreamUtils.trimLines(new ByteArrayInputStream(expected));
        StringBuffer s2 = StreamUtils.trimLines(new ByteArrayInputStream(result.getBytes()));
        
        assertEquals("Expected:\n" + s1 + "\nActual:\n" + s2, s1.toString(), s2.toString());
    }
}