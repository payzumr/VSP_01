package bank.BankPackage;

/**
* bank/BankPackage/EKontoAlreadyExistsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Mittwoch, 26. März 2014 15:55 Uhr MEZ
*/

public final class EKontoAlreadyExistsHolder implements org.omg.CORBA.portable.Streamable
{
  public bank.BankPackage.EKontoAlreadyExists value = null;

  public EKontoAlreadyExistsHolder ()
  {
  }

  public EKontoAlreadyExistsHolder (bank.BankPackage.EKontoAlreadyExists initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = bank.BankPackage.EKontoAlreadyExistsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    bank.BankPackage.EKontoAlreadyExistsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return bank.BankPackage.EKontoAlreadyExistsHelper.type ();
  }

}
