package com.bilgeadam.springbootrest.model;

import org.springframework.security.core.GrantedAuthority;

// SimpleGrantedAuthority kullanılabilir
public class Role implements GrantedAuthority
{
	private static final long serialVersionUID = 3661468982812594323L;

	private String name;

	public Role(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public String getAuthority()
	{
		return name;
	}
}