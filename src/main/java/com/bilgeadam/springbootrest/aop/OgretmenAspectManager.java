package com.bilgeadam.springbootrest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
//@Component
//https://www.baeldung.com/spring-aop-pointcut-tutorial
public class OgretmenAspectManager
{
//	@Pointcut(value = "execution(public * *(..))")
	// içinde ogretmen kelimesi geçen sınıfları yakaladım
//	@Pointcut(value = "execution(* com.bilgeadam.springbootrest.*.Ogretmen*.*(..))")
//	@Pointcut(value = "execution(* com.bilgeadam.springbootrest.configuration.MyFilter.*(..))")
	// sadece ogretmencontroller
//	@Pointcut(value = "execution(* com.bilgeadam.springbootrest.controller.OgretmenController.*(..))")
	// kendi projemde hepsini yakalamak istersek
//	@Pointcut(value = "execution(public * com.bilgeadam.springbootrest..*(..))")
	// belli bir paketteki bütün class ve metodlar
	@Pointcut(value = "within(com.bilgeadam.springbootrest..*)")
//	@Pointcut(value = "within(org.springframework.web.servlet..*)")
	private void mypointcut()
	{
		System.err.println("==> all methods");
	}

	// yukarıdaki metodun adı
	@Before(value = "mypointcut()")
	public void beforemypointcut(JoinPoint point)
	{
		System.err.println("==> before mypointcut " + point.getSignature());
	}

	@After("mypointcut()")
	public void aftermypointcut(JoinPoint point)
	{
		System.err.println("==> after mypointcut " + point.getSignature());
	}

	// hem öncesi hem sonrası
	@Around(value = "mypointcut()")
	public Object aroundOgretmenPointcut(ProceedingJoinPoint point) throws Throwable
	{
		System.err.println("==> around mypointcut");
		long startTime = System.currentTimeMillis();
		Object object = point.proceed();
		long endtime = System.currentTimeMillis();
		System.err.println("Class Name: " + point.getSignature().getDeclaringTypeName() + ". Method Name: " + point.getSignature().getName() + ". Time taken for Execution is : " + (endtime - startTime) + "ms");
		System.err.println("==> around mypointcut 2");
		return object;
	}

	// all implementations of interface
	// within(JPARepositoryImpl+)
}