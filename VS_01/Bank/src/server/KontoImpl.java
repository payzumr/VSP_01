package server;

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

import bank.*;
import bank.KontoPackage.EInvalidAmount;
import bank.KontoPackage.ENotEnoughMoney;

public class KontoImpl extends KontoPOA implements Konto {

	double kontostand = 0;
	final String kontonummer;
	BankImpl bank;

	public KontoImpl(final String kontonummer, BankImpl bank) {
		this.kontonummer = kontonummer;
		this.bank = bank;
	}

	@Override
	public double kontostand() {
		return this.kontostand;

	}

	@Override
	public String kontonr() {

		return this.kontonummer;

	}

	@Override
	public void einzahlen(double betrag) throws EInvalidAmount {
		if (betrag <= 0) {
			throw new EInvalidAmount(this.kontonummer);
		} else {
			this.kontostand += betrag;
			bank.aktualisieren("Auf das Konto mit der Kontonummer "
					+ this.kontonummer + " wurde ein Betrag von " + betrag
					+ " eingezahlt. Der aktuelle Kontostand ist jetzt "
					+ this.kontostand + ".\n");
		}

	}

	@Override
	public void auszahlen(double betrag) throws EInvalidAmount, ENotEnoughMoney {

		if (betrag <= 0) {
			throw new EInvalidAmount(this.kontonummer);
		} else if (this.kontostand - betrag < 0) {
			throw new ENotEnoughMoney(this.kontonummer);
		} else {
			this.kontostand -= betrag;
			bank.aktualisieren("Von dem Konto mit der Kontonummer "
					+ this.kontonummer + " wurde ein Betrag von " + betrag
					+ " abgehoben. Der aktuelle Kontostand ist jetzt "
					+ this.kontostand + ".\n");
		}

	}

	@Override
	public void transfer(double betrag, Konto toKonto) throws EInvalidAmount {
		if (betrag <= 0) {
			throw new EInvalidAmount(this.kontonummer);
		} else {
//			try {
//				this.auszahlen(betrag);
				toKonto.einzahlen(betrag);
				bank.aktualisieren("Von dem Konto mit der Kontonummer "
						+ this.kontonummer + " wurde ein Betrag von " + betrag
						+ " abgehoben. Der aktuelle Kontostand ist jetzt "
						+ this.kontostand + ".\n");
//			} catch (ENotEnoughMoney e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}

	}

	@Override
	public boolean _is_equivalent(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int _hash(int maximum) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object _duplicate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _release() {
		// TODO Auto-generated method stub

	}

	@Override
	public Request _request(String operation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request _create_request(Context ctx, String operation,
			NVList arg_list, NamedValue result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request _create_request(Context ctx, String operation,
			NVList arg_list, NamedValue result, ExceptionList exclist,
			ContextList ctxlist) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Policy _get_policy(int policy_type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DomainManager[] _get_domain_managers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object _set_policy_override(Policy[] policies,
			SetOverrideType set_add) {
		// TODO Auto-generated method stub
		return null;
	}

}
