package example;

import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.milyn.io.StreamUtils.compareCharStreams;
import static org.milyn.io.StreamUtils.readStreamAsString;
import org.milyn.payload.JavaResult;
import org.xml.sax.SAXException;

public class EDItoJavaTest {

    @Test
    public void test() throws IOException, SAXException {
        String expected = readStreamAsString(getClass().getResourceAsStream("expected.xml"));
        Main smooksMain = new Main();

        JavaResult result = smooksMain.runSmooksTransform();
        XStream xstream = new XStream();
        String actual = xstream.toXML(result.getBean("order"));
        actual = actual.replaceFirst("<date>.*</date>", "<date/>");
        boolean matchesExpected = compareCharStreams(new StringReader(expected), new StringReader(actual));

        if (!matchesExpected) {
            assertEquals("Actual does not match expected.", expected, actual);
        }
    }
}
