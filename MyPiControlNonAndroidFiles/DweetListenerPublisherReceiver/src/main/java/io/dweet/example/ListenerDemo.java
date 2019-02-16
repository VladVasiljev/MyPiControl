package io.dweet.example;

import com.google.gson.JsonObject;
import io.dweet.helper.Helper;
import io.dweet.DweetIO;
import java.text.ParseException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demonstration on using Java client for dweet.io.
 *
 * @author Khaled Bakhit <kb@khaled-bakhit.com>
 */
public class ListenerDemo implements Runnable {

    public static void main(String[] args) throws IOException, ParseException {

        Properties properties = Helper.getProperties("config.properties");

        String thingName = properties.getProperty("thing");
        thingName="mypistats";
        ListenerDemo demo = new ListenerDemo(thingName);
        Thread thread = new Thread(demo);
        thread.start();
    }
    
    private final String thingName;

    public ListenerDemo(String name) {
        this.thingName = name;
    }

    @Override
    public void run() {
        // the listen method block so I have launched it in a separate thread
        DweetIO.listen(thingName, new DummyListener());
    }

}
