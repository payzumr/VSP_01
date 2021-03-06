package bank;


/**
* bank/BankPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Mittwoch, 26. März 2014 15:55 Uhr MEZ
*/

public abstract class BankPOA extends org.omg.PortableServer.Servant
 implements bank.BankOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getKontoliste", new java.lang.Integer (0));
    _methods.put ("neu", new java.lang.Integer (1));
    _methods.put ("loeschen", new java.lang.Integer (2));
    _methods.put ("hole", new java.lang.Integer (3));
    _methods.put ("monitorHinzufuegen", new java.lang.Integer (4));
    _methods.put ("monitorEntfernen", new java.lang.Integer (5));
    _methods.put ("exit", new java.lang.Integer (6));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {

  //holt die aktuelle Kontoliste, Rýckgabewert soll die Gesamtzahl der Konten angeben
       case 0:  // bank/Bank/getKontoliste
       {
         bank.TKontolisteHolder kontoliste = new bank.TKontolisteHolder ();
         int $result = (int)0;
         $result = this.getKontoliste (kontoliste);
         out = $rh.createReply();
         out.write_long ($result);
         bank.TKontolisteHelper.write (out, kontoliste.value);
         break;
       }

       case 1:  // bank/Bank/neu
       {
         try {
           String kontonr = in.read_string ();
           bank.Konto $result = null;
           $result = this.neu (kontonr);
           out = $rh.createReply();
           bank.KontoHelper.write (out, $result);
         } catch (bank.BankPackage.EKontoAlreadyExists $ex) {
           out = $rh.createExceptionReply ();
           bank.BankPackage.EKontoAlreadyExistsHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // bank/Bank/loeschen
       {
         try {
           String kontonr = in.read_string ();
           this.loeschen (kontonr);
           out = $rh.createReply();
         } catch (bank.BankPackage.EKontoNotFound $ex) {
           out = $rh.createExceptionReply ();
           bank.BankPackage.EKontoNotFoundHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // bank/Bank/hole
       {
         try {
           String kontonr = in.read_string ();
           bank.Konto $result = null;
           $result = this.hole (kontonr);
           out = $rh.createReply();
           bank.KontoHelper.write (out, $result);
         } catch (bank.BankPackage.EKontoNotFound $ex) {
           out = $rh.createExceptionReply ();
           bank.BankPackage.EKontoNotFoundHelper.write (out, $ex);
         }
         break;
       }

       case 4:  // bank/Bank/monitorHinzufuegen
       {
         bank.Monitor theMonitor = bank.MonitorHelper.read (in);
         this.monitorHinzufuegen (theMonitor);
         out = $rh.createReply();
         break;
       }

       case 5:  // bank/Bank/monitorEntfernen
       {
         bank.Monitor theMonitor = bank.MonitorHelper.read (in);
         this.monitorEntfernen (theMonitor);
         out = $rh.createReply();
         break;
       }


  //Dient zum Beenden der Bankanwendung. Sorgt dafuer, dass die Bank und alle registrierten Monitore beendet werden.
       case 6:  // bank/Bank/exit
       {
         this.exit ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:bank/Bank:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Bank _this() 
  {
    return BankHelper.narrow(
    super._this_object());
  }

  public Bank _this(org.omg.CORBA.ORB orb) 
  {
    return BankHelper.narrow(
    super._this_object(orb));
  }


} // class BankPOA
