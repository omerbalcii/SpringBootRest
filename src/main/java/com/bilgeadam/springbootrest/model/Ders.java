package com.bilgeadam.springbootrest.model;

public class Ders
{
	private long ID;
	private long OGRETMEN_ID;
	private long KONU_ID;

	public Ders()
	{
	}

	public Ders(long iD, long OGRETMEN_ID, long kONU_ID)
	{
		ID = iD;
		this.OGRETMEN_ID = OGRETMEN_ID;
		KONU_ID = kONU_ID;
	}

	public Ders(long oGRETMEN_ID, long kONU_ID)
	{
		OGRETMEN_ID = oGRETMEN_ID;
		KONU_ID = kONU_ID;
	}

	public long getID()
	{
		return ID;
	}

	public void setID(long iD)
	{
		ID = iD;
	}

	public long getOGRETMEN_ID()
	{
		return OGRETMEN_ID;
	}

	public void setOGRETMEN_ID(long oGRETMEN_ID)
	{
		OGRETMEN_ID = oGRETMEN_ID;
	}

	public long getKONU_ID()
	{
		return KONU_ID;
	}

	public void setKONU_ID(long kONU_ID)
	{
		KONU_ID = kONU_ID;
	}

	@Override
	public String toString()
	{
		return "Ders [ID=" + ID + ", OGRETMEN_ID=" + OGRETMEN_ID + ", KONU_ID=" + KONU_ID + "]";
	}

}