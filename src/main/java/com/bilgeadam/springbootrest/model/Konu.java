package com.bilgeadam.springbootrest.model;

public class Konu
{
	private long ID;
	private String NAME;

	public Konu()
	{
	}

	public Konu(String nAME)
	{
		NAME = nAME;
	}

	public Konu(long iD, String nAME)
	{
		ID = iD;
		NAME = nAME;
	}

	public long getID()
	{
		return ID;
	}

	public void setID(long iD)
	{
		ID = iD;
	}

	public String getNAME()
	{
		return NAME;
	}

	public void setNAME(String nAME)
	{
		NAME = nAME;
	}

	@Override
	public String toString()
	{
		return "Konu [ID=" + ID + ", NAME=" + NAME + "]";
	}

}
