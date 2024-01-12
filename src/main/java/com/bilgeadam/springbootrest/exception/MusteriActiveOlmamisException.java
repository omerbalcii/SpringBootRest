package com.bilgeadam.springbootrest.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class MusteriActiveOlmamisException extends InternalAuthenticationServiceException
{
	public MusteriActiveOlmamisException(String msg)
	{
		super(msg);
	}
}
