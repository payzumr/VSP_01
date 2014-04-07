package monitor;

import java.util.Properties;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import bank.BankHelper;
import bank.MonitorHelper;

public class Monitor {
	
	static ORB orb;
	static boolean lauft = true;
	
	public static void main(String[] args) {
		
		try {
			// ORB Eigenschaften setzen
			Properties props = new Properties();
			props.put("org.omg.CORBA.ORBInitialPort", "1050");
			props.put("org.omg.CORBA.ORBInitialHost", "localhost");
			orb = ORB.init(args, props);

			// Referenz von rootPOA holen und POA Manager aktivieren
			POA rootPoa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			rootPoa.the_POAManager().activate();

			// Servant erzeugen
			MonitorImpl monitor = new MonitorImpl();
			
			bank.Monitor mon = MonitorHelper.narrow(rootPoa.servant_to_reference(monitor));
			
			// Referenz für den Servant besorgen
			//Monitor ref = rootPoa.servant_to_reference(monitor);
			
			// Referenz zum Namensdiesnt (root naming context) holen
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			
			// Verwendung von NamingContextExt, ist Teil der Interoperable
			// Naming Service (INS) Spezifikation.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			// Objektreferenz mit Namen "bank" besorgen
			org.omg.CORBA.Object obj = ncRef.resolve_str(args[0]);

			// Downcast Corba-Objekt -> Bank
			bank.Bank bank = BankHelper.narrow(obj);

			//Monitor registrieren/anmelden
			bank.monitorHinzufuegen(mon);
			System.out.println("Monitor ready and connected to: " + args[0]);
			
			

	        while (lauft){ 
	        	Scanner scanner = new Scanner(System.in); 
	            String s = scanner.next(); 
	            if(s.equals("q")) break; 
	        } 
			bank.monitorEntfernen(mon);
			
		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("Monitor Exiting ...");
	}
}
