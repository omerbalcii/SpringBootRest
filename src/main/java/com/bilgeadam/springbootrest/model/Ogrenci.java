package com.bilgeadam.springbootrest.model;

public class Ogrenci
{
	private long ID;
	private String NAME;
	private long OGR_NUMBER;
	private long YEAR;

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

	public Ogrenci()
	{
	}

	public Ogrenci(long iD, String nAME, long oGR_NUMBER, long yEAR)
	{
		ID = iD;
		NAME = nAME;
		OGR_NUMBER = oGR_NUMBER;
		YEAR = yEAR;
	}

	public Ogrenci(String nAME, long oGR_NUMBER, long yEAR)
	{
		NAME = nAME;
		OGR_NUMBER = oGR_NUMBER;
		YEAR = yEAR;
	}

	public void setNAME(String nAME)
	{
		NAME = nAME;
	}

	public long getOGR_NUMBER()
	{
		return OGR_NUMBER;
	}

	public void setOGR_NUMBER(long oGR_NUMBER)
	{
		OGR_NUMBER = oGR_NUMBER;
	}

	public long getYEAR()
	{
		return YEAR;
	}

	public void setYEAR(long yEAR)
	{
		YEAR = yEAR;
	}

	@Override
	public String toString()
	{
		return "Ogrenci [ID=" + ID + ", NAME=" + NAME + ", OGR_NUMBER=" + OGR_NUMBER + ", YEAR=" + YEAR + "]";
	}
}
