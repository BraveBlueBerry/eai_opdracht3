package hanze.nl.bussimulator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import hanze.nl.tijdtools.TijdFuncties;

public class Runner {

	private static HashMap<Integer,ArrayList<Bus>> busStart = new HashMap<Integer,ArrayList<Bus>>();
	private static ArrayList<Bus> actieveBussen = new ArrayList<Bus>();
	private static int interval=1000;
	private static int syncInterval=5;

	public static void main(String[] args) throws InterruptedException {

		BusCollection busCollection = new BusCollection();
		BusController busController = new BusController(busCollection);
		int tijd=0;
		while (busController.shouldRun()) {
			System.out.println("De tijd is:" + tijd);
			busController.startBussen(tijd);
			int volgende = busController.nextBusToRun();

			busController.moveBussen(tijd);
			busController.sendETAs(tijd);
			Thread.sleep(interval);
			tijd++;
		}
	}

	/* Om de tijdsynchronisatie te gebruiken moet de onderstaande main gebruikt worden
	 * 
	public static void main(String[] args) throws InterruptedException {
		int tijd=0;
		int counter=0;
		TijdFuncties tijdFuncties = new TijdFuncties();
		tijdFuncties.initSimulatorTijden(interval,syncInterval);
		int volgende = initBussen();
		while ((volgende>=0) || !actieveBussen.isEmpty()) {
			counter=tijdFuncties.getCounter();
			tijd=tijdFuncties.getTijdCounter();
			System.out.println("De tijd is:" + tijdFuncties.getSimulatorWeergaveTijd());
			volgende = (counter==volgende) ? startBussen(counter) : volgende;
			moveBussen(tijd);
			sendETAs(tijd);
			tijdFuncties.simulatorStep();
		}
	}
		 */

}
