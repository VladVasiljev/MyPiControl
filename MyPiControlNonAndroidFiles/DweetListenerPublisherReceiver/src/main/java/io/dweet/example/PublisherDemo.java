package io.dweet.example;

import com.google.gson.JsonObject;
import io.dweet.helper.Helper;
import io.dweet.DweetIO;
import java.text.ParseException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Demonstration on using Java client for dweet.io.
 *
 * @author Khaled Bakhit <kb@khaled-bakhit.com>
 */
public class PublisherDemo {

    public static void main(String[] args) throws IOException, ParseException {
                  
        String thingName = "mypicontrolboardBuzzer";
            
        JsonObject json = new JsonObject();
        json.addProperty("BuzzerStatus", "true");
        json.addProperty("SampleRate",0);
        
        boolean success = DweetIO.publish(thingName, json);
        if (success){
            System.out.println("We have published");
        }
        else {
            System.out.println("we dun goofed");
        }


    }

}
