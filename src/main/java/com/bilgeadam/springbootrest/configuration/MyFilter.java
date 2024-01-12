package com.bilgeadam.springbootrest.configuration;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class MyFilter implements Filter
{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
//		((HttpServletRequest)request).getInputStream()
//		UserRepository.finduser(username, password);
//		System.err.println("filter metodum");
		chain.doFilter(request, response);
	}
}
