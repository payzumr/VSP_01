package bank.BankPackage;

/**
* bank/BankPackage/EKontoNotFoundHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Mittwoch, 26. März 2014 15:55 Uhr MEZ
*/

public final class EKontoNotFoundHolder implements org.omg.CORBA.portable.Streamable
{
  public bank.BankPackage.EKontoNotFound value = null;

  public EKontoNotFoundHolder ()
  {
  }

  public EKontoNotFoundHolder (bank.BankPackage.EKontoNotFound initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = bank.BankPackage.EKontoNotFoundHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    bank.BankPackage.EKontoNotFoundHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return bank.BankPackage.EKontoNotFoundHelper.type ();
  }

}
