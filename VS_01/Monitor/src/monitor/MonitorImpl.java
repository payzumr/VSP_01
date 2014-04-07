package monitor;

import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;

import bank.Monitor;
import bank.MonitorOperations;
import bank.MonitorPOA;

public class MonitorImpl extends MonitorPOA {

	public MonitorImpl() {
	}

	@Override
	public void meldung(String msg) {
		System.out.println(msg);
	}

	@Override
	public void exit() {
		try {
			new Thread() {

				public void run() {
					System.exit(0);
				}
			}.start();
		} catch (Exception e) {
			System.err.println("Allg. ERROR: " + e);
			e.printStackTrace(System.out);
		}

	}

}
