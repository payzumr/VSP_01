package client;

import bank.*;
import java.util.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import bank.BankHelper;

public class Client {

	// public static void demo(Bank bank) {
	// try {
	// System.out.println("Lege neues Konto an\n");
	// bank.neu("123");
	// System.out.println("Konto angelegt\n");
	// bank.loeschen("123");
	// System.out.println("gelöscht neu anlegen");
	// bank.neu("123");
	// System.out.println("Konto angelegt\n");
	// System.out.println("Kontostand alt "
	// + bank.hole("123").kontostand());
	// bank.hole("123").einzahlen(50.0);
	// System.out.println("50 eingezahlt");
	// System.out.println("Kontostand " + bank.hole("123").kontostand());
	//
	// } catch (Exception e) {
	// System.err.println(e);
	// System.exit(1);
	// }
	//
	// }

	public static void main(String args[]) {

		try {

			Properties props = new Properties();
			props.put("org.omg.CORBA.ORBInitialPort", "1050");
			props.put("org.omg.CORBA.ORBInitialHost", "localhost");
			ORB orb = ORB.init(args, props);

			// NamingContext besorgen
			NamingContextExt nc = NamingContextExtHelper.narrow(orb
					.resolve_initial_references(args[0]));

			// Objektreferenz mit Namen "bank" besorgen
			org.omg.CORBA.Object obj = nc.resolve_str(args[2]);

			// Down-Cast
			Bank bank = BankHelper.narrow(obj);
			

			switch (args[1]) {
			case "ende":
				bank.exit();
				break;
			case "liste":
				TKontolisteHolder liste = new TKontolisteHolder();
				bank.getKontoliste(liste);

				for (int i = 0; i < liste.value.length; i++) {
					System.out.println("Kontonummer: "
							+ liste.value[i].kontonr() + " Kontostand "
							+ liste.value[i].kontostand());
				}

				break;
			case "anlegen":
				bank.neu(args[3]);
				System.out.println("Client: bank anglegt Nummer: " + args[3]);
				break;
			case "anlegen500":
				for(Integer i = 0; i< 500 ; i++){
					bank.neu(i.toString());
				}
				break;
			case "loeschen":
				bank.loeschen(args[3]);
				System.out.println("Client: bank gelöscht Nummer: " + args[3]);
				break;
			case "einzahlen":
				try {
					double betrag = Double.parseDouble(args[4]);
					bank.hole(args[3]).einzahlen(betrag);
					System.out.println("Es wurden: " + args[4] + " eingezahlt");
				} catch (NumberFormatException e) {
					System.out
							.println("Bitte geben sie einen gueltigen Zahlenbetrag ein");
				}
				break;
			case "auszahlen":
				try {
					double betrag = Double.parseDouble(args[4]);
					bank.hole(args[3]).auszahlen(betrag);
					System.out.println("Es wurden: " + args[4] + " ausgezahlt");
				} catch (NumberFormatException e) {
					System.out
							.println("Bitte geben sie einen gueltigen Zahlenbetrag ein");
				}
				break;
			case "ueberweisen":
				try {
					org.omg.CORBA.Object obj2 = nc.resolve_str(args[4]);
					Bank bank2 = BankHelper.narrow(obj2);
					double betrag = Double.parseDouble(args[6]);
					bank.hole(args[3]).auszahlen(betrag);
					bank.hole(args[3]).transfer(betrag, bank2.hole(args[5]));
				} catch (NumberFormatException e) {
					System.out
							.println("Bitte geben sie einen gueltigen Zahlenbetrag ein");
				}
				break;
			default:
				System.out.println("hau ab!");
			}

			// demo(bank);

		} catch (Exception ex) {
			System.err.println(ex);
			System.exit(1);
		}
	}

}
