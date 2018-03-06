package example;

import java.io.IOException;
import org.apache.xerces.util.XML11Char;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.milyn.io.StreamUtils.readStreamAsString;
import org.xml.sax.SAXException;

public class EDItoXMLTest {

    @Test
    public void test() throws IOException, SAXException {
        String expected = readStreamAsString(getClass().getResourceAsStream("expected-2.xml"));
        String result = Main2.runSmooksTransform();
        
        XMLUnit.setIgnoreWhitespace(true);
        XMLAssert.assertXMLEqual(expected, result);
    }
}
