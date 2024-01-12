package com.bilgeadam.springbootrest.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bilgeadam.springbootrest.model.Role;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//OncePerRequestFilter sayesinde security context 'ine attığımız sadece requestte geçerli oluyor
public class JWTAuthorizationFilter extends OncePerRequestFilter
{
	// dofilter yerine spring security classları kullandığım için dointernalfilter
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		// bu aşamada kullanıcı zaten doğrulandı ve token ı verildi
		// yetki gerektirmeyen endpoint 'lerde bile Authorization kontrol ediliyor malesef
		String token = req.getHeader("Authorization");
		// Bearer 30948hgb57gbhg9wpuısgh==
		if (token != null)
		{
			try
			{
				// kullanıcı adı şifre kontrolü DEĞİL !!! jwt geçerlilik kontrolü
				// Bearer ile gönderilir genelde
				String mytoken = JWT.require(Algorithm.HMAC512("MY_SECRET_KEY".getBytes())).build().verify(token.replace("Bearer ", "")).getSubject();
				if (mytoken != null)
				{
					// user-USER
					// admin-ADMIN
					String username = mytoken.split("-")[0];
					List<Role> auth = Arrays.asList(mytoken.split("-")[1].split(",")).stream().map(item -> new Role(item)).collect(Collectors.toList());
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, auth);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					chain.doFilter(req, res);
				}
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
				res.getWriter().write("Token exception => " + e.getMessage());
				// benden sonraki filtreler response umu bozuyor o yüzden chain yok
				// chain.doFilter(req, res);
			}
		}
		else
		{
			System.err.println("Token yok ama header gelmiş");
			// token yok, nasıl olsa security config 'den patlayacağım
			chain.doFilter(req, res);
		}
	}
}