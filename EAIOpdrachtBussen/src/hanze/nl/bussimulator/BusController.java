package hanze.nl.bussimulator;

import java.util.Collections;
import java.util.Iterator;

public class BusController {

    private BusCollection busCollection;
    public BusController(BusCollection bussen) {
        this.busCollection = bussen;
    }
    public void startBussen(int time){
        busCollection.makeActive(time);
    }

    public boolean shouldRun() {
        return !busCollection.getBussesReadyToGo().isEmpty();
    }

    public int nextBusToRun() {
        return (shouldRun() ? Collections.min(busCollection.getBussesReadyToGo().keySet()) : -1);
    }

    public void moveBussen(int nu){
        Iterator<Bus> itr = busCollection.getActiveBusses().iterator();
        while (itr.hasNext()) {
            Bus bus = itr.next();
            boolean eindpuntBereikt = bus.move();
            if (eindpuntBereikt) {
                bus.sendLastETA(nu);
                itr.remove();
            }
        }
    }

    public void sendETAs(int nu){
        Iterator<Bus> itr = busCollection.getActiveBusses().iterator();
        while (itr.hasNext()) {
            Bus bus = itr.next();
            bus.sendETAs(nu);
        }
    }
}
