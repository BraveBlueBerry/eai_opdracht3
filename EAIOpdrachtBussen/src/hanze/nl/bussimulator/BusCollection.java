package hanze.nl.bussimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BusCollection {
    private HashMap<Integer,ArrayList<Bus>> bussesReadyToGo = new HashMap<Integer,ArrayList<Bus>>();
    private ArrayList<Bus> actieveBussen = new ArrayList<>();

    public BusCollection() {
        initBussen();
    }

    HashMap<Integer,ArrayList<Bus>> getBussesReadyToGo() {
        return bussesReadyToGo;
    }

    ArrayList<Bus> getActiveBusses() {
        return actieveBussen;
    }

    void makeActive(int time) {
        for (Bus bus : bussesReadyToGo.get(time)){
            actieveBussen.add(bus);
        }
        bussesReadyToGo.remove(time);
    }

    private void addBus(int starttijd, Bus bus){
        ArrayList<Bus> bussen = new ArrayList<Bus>();
        if (bussesReadyToGo.containsKey(starttijd)) {
            bussen = bussesReadyToGo.get(starttijd);
        }
        bussen.add(bus);
        bussesReadyToGo.put(starttijd,bussen);
        bus.setbusID(starttijd);
    }



    private void initBussen(){
        Bus bus1=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1);
        Bus bus2=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, 1);
        Bus bus3=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, 1);
        Bus bus4=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
        Bus bus5=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
        Bus bus6=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, 1);
        Bus bus7=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, 1);
        Bus bus8=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1);
        Bus bus9=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
        Bus bus10=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
        addBus(3, bus1);
        addBus(5, bus2);
        addBus(4, bus3);
        addBus(6, bus4);
        addBus(3, bus5);
        addBus(5, bus6);
        addBus(4, bus7);
        addBus(6, bus8);
        addBus(12, bus9);
        addBus(10, bus10);
        Bus bus11=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1);
        Bus bus12=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, -1);
        Bus bus13=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, -1);
        Bus bus14=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
        Bus bus15=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
        Bus bus16=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, -1);
        Bus bus17=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, -1);
        Bus bus18=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1);
        Bus bus19=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
        Bus bus20=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
        addBus(3, bus11);
        addBus(5, bus12);
        addBus(4, bus13);
        addBus(6, bus14);
        addBus(3, bus15);
        addBus(5, bus16);
        addBus(4, bus17);
        addBus(6, bus18);
        addBus(12, bus19);
        addBus(10, bus20);
    }

    public int getUpcomingBusIndex() {
        return Collections.min(bussesReadyToGo.keySet());
    }
}
