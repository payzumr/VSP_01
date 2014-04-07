package server;

import java.util.List;

import bank.Monitor;

public class MonitorThread extends Thread {
	private String sendString;
	private List<Monitor> monitors; 

	public MonitorThread(String sendString, List<Monitor> monitors) {
		super();
		this.sendString = sendString;
		this.monitors = monitors;
	}

	@Override
	public void run() {
		if (!monitors.isEmpty()) {
			for (Monitor e : monitors) {
				e.meldung(sendString);
			}
		}
	}

}
