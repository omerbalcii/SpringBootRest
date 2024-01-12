package com.bilgeadam.springbootrest.model;

public class DersOgrenciDTO
{
	private long id;
	private DersDTO ders;
	private Ogrenci ogrenci;
	private int DEVAMSIZLIK;
	private int NOTE;

	public DersOgrenciDTO()
	{
	}

	public DersOgrenciDTO(long id, DersDTO ders, Ogrenci ogrenci, int dEVAMSIZLIK, int nOTE)
	{
		this.id = id;
		this.ders = ders;
		this.ogrenci = ogrenci;
		DEVAMSIZLIK = dEVAMSIZLIK;
		NOTE = nOTE;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
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

	public DersDTO getDers()
	{
		return ders;
	}

	public void setDers(DersDTO ders)
	{
		this.ders = ders;
	}

	public Ogrenci getOgrenci()
	{
		return ogrenci;
	}

	public void setOgrenci(Ogrenci ogrenci)
	{
		this.ogrenci = ogrenci;
	}

	@Override
	public String toString()
	{
		return "DersOgrenciDTO [ogrenci=" + ogrenci + ", ders=" + ders + "]";
	}
}
