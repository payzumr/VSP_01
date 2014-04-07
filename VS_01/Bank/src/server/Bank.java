package server;

import java.util.Properties;

import org.omg.CORBA.*;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;

import bank.BankHelper;

public class Bank {

	static public ORB orb;

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("Bitte geben sie einen Namen fuer die Bank ein");
		} else {

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
				BankImpl bank = new BankImpl(rootPoa);

				// Referenz für den Servant besorgen
				org.omg.CORBA.Object ref = rootPoa.servant_to_reference(bank);

				// Downcast Corba-Objekt -> Bank
				bank.Bank href = BankHelper.narrow(ref);

				// Referenz zum Namensdiesnt (root naming context) holen
				org.omg.CORBA.Object objRef = orb
						.resolve_initial_references("NameService");

				// Verwendung von NamingContextExt, ist Teil der Interoperable
				// Naming Service (INS) Spezifikation.
				NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

				// binde die Object Reference an einen Namen
				String name = args[0];
				NameComponent path[] = ncRef.to_name(name);
				ncRef.rebind(path, href);
				System.out.println("BankServer ready and waiting ...");

				// Orb starten und auf Clients warten
				orb.run();
			} catch (Exception e) {
				System.err.println("ERROR: " + e);
				e.printStackTrace(System.out);
			}
			System.out.println("BankServer Exiting ...");
		}

	}

}