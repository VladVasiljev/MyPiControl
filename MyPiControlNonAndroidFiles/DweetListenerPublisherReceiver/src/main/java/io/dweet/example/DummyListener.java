package io.dweet.example;

import io.dweet.Dweet;
import io.dweet.DweetListener;

/**
 *
 * @author dcarr
 */
public class DummyListener implements DweetListener {

    @Override
    public void dweetReceived(Dweet dweet) {
        System.out.println("Listener receives " + dweet);
    }

    @Override
    public void stopListening(String thingName) {
        
    }

    @Override
    public boolean isStillListeningTo(String thingName) {
        return true;
    }

    @Override
    public void stoppedListening(String thingName) {
    }

    @Override
    public void connectionLost(String thingName, Exception e) {
    }
    
}
