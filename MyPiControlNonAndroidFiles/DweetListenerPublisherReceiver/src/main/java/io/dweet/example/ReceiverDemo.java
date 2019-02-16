package io.dweet.example;

import com.google.gson.JsonObject;
import io.dweet.helper.Helper;
import io.dweet.Dweet;
import io.dweet.DweetIO;
import java.text.ParseException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demonstration on using Java client for dweet.io.
 *
 * @author Khaled Bakhit <kb@khaled-bakhit.com>
 */
public class ReceiverDemo {

    public static void main(String[] args) throws IOException, ParseException {
        
        Properties properties = Helper.getProperties("config.properties");
          
        String thingName = properties.getProperty("thing");
            thingName="mypistats";
        Dweet dweet = DweetIO.getLatestDweet(thingName);
        System.out.println(dweet);

        // we must sleep to avoid being rate limited
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReceiverDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Get all Dweets ( up to 500 in the last 24 hours )
        List<Dweet> dweets = DweetIO.getAllDweets(thingName);
        for (Dweet d : dweets) {
            System.out.println(d);
        }
    }

}
