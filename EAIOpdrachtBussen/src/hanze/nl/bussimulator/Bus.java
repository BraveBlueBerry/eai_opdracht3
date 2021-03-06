package hanze.nl.bussimulator;

import hanze.nl.bussimulator.Halte.Positie;

public class Bus{

	private Bedrijven bedrijf;
	private Lijnen lijn;
	private int halteNummer;
	private int totVolgendeHalte;
	private int richting;
	private boolean bijHalte;
	private String busID;
	private boolean eindpuntBereikt;
	
	Bus(Lijnen lijn, Bedrijven bedrijf, int richting){
		this.lijn=lijn;
		this.bedrijf=bedrijf;
		this.richting=richting;
		this.halteNummer = -1;
		this.totVolgendeHalte = 0;
		this.bijHalte = false;
		this.eindpuntBereikt = false;
		this.busID = "Niet gestart";
	}
	
	void setbusID(int starttijd){
		this.busID=starttijd+lijn.name()+richting;
	}
	
	private void naarVolgendeHalte(){
		Positie volgendeHalte = lijn.getHalte(halteNummer+richting).getPositie();
		totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
	}
	
	private boolean halteBereikt(){
		halteNummer+=richting;
		bijHalte=true;
		if ((halteNummer>=lijn.getLengte()-1) || (halteNummer == 0)) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n", 
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
			return true;
		}
		System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n",
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
		naarVolgendeHalte();
		return false;
	}
	
	private void start() {
		halteNummer = (richting==1) ? 0 : lijn.getLengte()-1;
		System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n", 
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));		
		naarVolgendeHalte();
	}

	private void setEindpuntBereikt(boolean bereikt){
	    eindpuntBereikt = bereikt;
    }

	public boolean getEindpuntBereikt(){
	    return getEindpuntBereikt();
    }
	
	void move(){
		bijHalte=false;
		if (halteNummer == -1) {
			start();
		}
		else {
			totVolgendeHalte--;
			if (totVolgendeHalte==0){
			    setEindpuntBereikt(halteBereikt());
			}
		}
	}
	
	void sendETAs(int nu){
		int i=0;
		Bericht bericht = new Bericht(lijn.name(),bedrijf.name(),busID,nu);
		if (bijHalte) {
			ETA eta = new ETA(lijn.getHalte(halteNummer).name(),lijn.getRichting(halteNummer),0);
			bericht.ETAs.add(eta);
		}
		Positie eerstVolgende=lijn.getHalte(halteNummer+richting).getPositie();
		int tijdNaarHalte=totVolgendeHalte+nu;
		for (i = halteNummer+richting ; !(i>=lijn.getLengte()) && !(i < 0); i=i+richting ){
			tijdNaarHalte+= lijn.getHalte(i).afstand(eerstVolgende);
			ETA eta = new ETA(lijn.getHalte(i).name(), lijn.getRichting(i),tijdNaarHalte);
			bericht.ETAs.add(eta);
			eerstVolgende=lijn.getHalte(i).getPositie();
		}
		bericht.eindpunt=lijn.getHalte(i-richting).name();
		sendBericht(bericht);
	}
	
	void sendLastETA(int nu){
		Bericht bericht = new Bericht(lijn.name(),bedrijf.name(),busID,nu);
		String eindpunt = lijn.getHalte(halteNummer).name();
		ETA eta = new ETA(eindpunt,lijn.getRichting(halteNummer),0);
		bericht.ETAs.add(eta);
		bericht.eindpunt = eindpunt;
		sendBericht(bericht);
	}

	private void sendBericht(Bericht bericht){
		//TODO verstuur een XML bericht naar de messagebroker.	
	}
}
