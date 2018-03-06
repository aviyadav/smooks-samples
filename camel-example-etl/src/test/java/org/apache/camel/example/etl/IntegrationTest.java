package org.apache.camel.example.etl;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

public class IntegrationTest {

    @Test
    public void testEtlRoutes() {
        try {
            Main.main("-duration", "5s", "-o", "target/site/cameldoc");
        } catch (Exception ex) {
            Logger.getLogger(IntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}