package io.dweet.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author dcarr
 */
public class Helper {
    
        public static Properties getProperties(String filename) {

        Properties properties = new Properties();
        InputStream input = null;

        try {
            // the working directory is the root of the project which is where I have placed config.properties
            input = new FileInputStream(filename);
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
    
}
