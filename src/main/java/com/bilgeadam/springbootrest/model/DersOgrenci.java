package com.bilgeadam.springbootrest.model;

public class DersOgrenci
{
	private long ID;
	private long DERS_ID;
	private long OGRENCI_ID;
	private int DEVAMSIZLIK;
	private int NOTE;

	public DersOgrenci()
	{
	}

	public DersOgrenci(long iD, long dERS_ID, long oGRENCI_ID, int dEVAMSIZLIK, int nOTE)
	{
		super();
		ID = iD;
		DERS_ID = dERS_ID;
		OGRENCI_ID = oGRENCI_ID;
		DEVAMSIZLIK = dEVAMSIZLIK;
		NOTE = nOTE;
	}

	public long getID()
	{
		return ID;
	}

	public void setID(long iD)
	{
		ID = iD;
	}

	public long getDERS_ID()
	{
		return DERS_ID;
	}

	public void setDERS_ID(long dERS_ID)
	{
		DERS_ID = dERS_ID;
	}

	public long getOGRENCI_ID()
	{
		return OGRENCI_ID;
	}

	public void setOGRENCI_ID(long oGRENCI_ID)
	{
		OGRENCI_ID = oGRENCI_ID;
	}

	public int getDEVAMSIZLIK()
	{
		return DEVAMSIZLIK;
	}

	public void setDEVAMSIZLIK(int dEVAMSIZLIK)
	{
		DEVAMSIZLIK = dEVAMSIZLIK;
	}

	public int getNOTE()
	{
		return NOTE;
	}

	public void setNOTE(int nOTE)
	{
		NOTE = nOTE;
	}

}
