package server;

import java.util.*;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import bank.BankPOA;
import bank.Konto;
import bank.KontoHelper;
import bank.Monitor;
import bank.TKontolisteHolder;
import bank.BankPackage.EKontoAlreadyExists;
import bank.BankPackage.EKontoNotFound;

public class BankImpl extends BankPOA {

	private List<Konto> Kntliste = new ArrayList<Konto>();
	private List<Monitor> monitors = new ArrayList<Monitor>();
	private POA poa;

	public BankImpl(POA poa) {
		this.poa = poa;
	}

	@Override
	public synchronized int getKontoliste(TKontolisteHolder kontoliste) {

		Konto[] a = Kntliste.toArray(new Konto[Kntliste.size()]);

		kontoliste.value = a;

		return kontoliste.value.length;
	}

	@Override
	public synchronized Konto neu(String kontonr) throws EKontoAlreadyExists {

		Konto knt = null;

		if (kontoVorhanden(kontonr)) {
			throw new EKontoAlreadyExists(kontonr);
		} else {
			KontoImpl impl = new KontoImpl(kontonr, this);
			try {
				// Downcast Corba-Objekt -> Konto
				knt = KontoHelper.narrow(poa.servant_to_reference(impl));
			} catch (ServantNotActive e) {
				e.printStackTrace();
			} catch (WrongPolicy e) {
				e.printStackTrace();
			}
			Kntliste.add(knt);

			aktualisieren("Es wurde ein neues Konto mit der Kontonummer "
					+ kontonr + " angelegt.\n");
		}
		return knt;
	}

	@Override
	public synchronized void loeschen(String kontonr) throws EKontoNotFound {

		if (!kontoVorhanden(kontonr)) {
			System.out.println("Konto not found");
			throw new EKontoNotFound(kontonr);
		} else {
			Konto tmp = null;
			for (Konto e : Kntliste) {
				if (e.kontonr().equals(kontonr)) {
					tmp = e;
				}
			}
			Kntliste.remove(tmp);

			aktualisieren("Es wurde das Konto mit der Kontonummer " + kontonr
					+ " geloescht.\n");
		}
	}

	@Override
	public synchronized Konto hole(String kontonr) throws EKontoNotFound {
		Konto tmp = null;
		if (!kontoVorhanden(kontonr)) {
			throw new EKontoNotFound(kontonr);
		} else {
			for (Konto e : Kntliste) {
				if (e.kontonr().equals(kontonr)) {
					System.out.print("");
					tmp = e;
				}
			}
			aktualisieren("Es wurde das Konto mit der Kontonummer " + kontonr
					+ " geholt.\n");
		}
		return tmp;
	}

	@Override
	public synchronized void monitorHinzufuegen(Monitor theMonitor) {

		monitors.add(theMonitor);
	}

	@Override
	public synchronized void monitorEntfernen(Monitor theMonitor) {
		if (!monitors.isEmpty() && monitors.contains(theMonitor)) {
			monitors.remove(theMonitor);
		}

		theMonitor.exit();
	}

	@Override
	public void exit() {
		if (!monitors.isEmpty()) {
			for (Monitor e : monitors) {
				e.exit();
			}
		}
		waiting();
	}

	public void aktualisieren(String message) {
		MonitorThread mtmp = new MonitorThread(message, monitors);
		mtmp.start();
	}

	static void waiting() {
		try {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Bank.getORB().shutdown(true);
					System.exit(0);
				}
			}.start();
		} catch (Exception e) {
			System.out.println("Fehler bei exit-Thread");
		}
	}

	private boolean kontoVorhanden(String kntnr) {
		boolean isThere = false;
		for (Konto e : Kntliste) {
			if (e.kontonr().equals(kntnr)) {
				isThere = true;
			}
		}

		return isThere;
	}

}
