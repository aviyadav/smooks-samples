package org.apache.camel.example.etl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends org.apache.camel.spring.Main {

    public static void main(String[] args) {
        try {
            new Main().run(args);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            ex.printStackTrace(System.err);
        }
    }
}
