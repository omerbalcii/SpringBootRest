package com.bilgeadam.springbootrest.model;

public class SystemUser // extends User // yapılabilir
{
	private String username = "";
	private String password = "";
	private boolean enabled = true;

	public SystemUser()
	{
	}

	public SystemUser(String username, String password, boolean enabled)
	{
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}